DESCRIPTION = "NI Linux RT imaging media ISO"
LICENSE = "MIT"

IMAGE_FSTYPES:append = " wic"

IMAGE_INSTALL = "\
		${ROOTFS_BOOTSTRAP_INSTALL} \
		packagegroup-ni-imagingmode \
"

IMAGE_FEATURES += "empty-root-password"

SRC_URI += "\
	file://grubenv_non_ni_target \
	file://unicode.pf2 \
"

SAFEMODE_IMAGE = "nilrt-safemode-rootfs"

kernel_fixup() {
	# Move kernel image from /boot/runmode to /boot/
	mv "${IMAGE_ROOTFS}/${KERNEL_IMAGEDEST}/$(readlink "${IMAGE_ROOTFS}/${KERNEL_IMAGEDEST}/bzImage")" "${IMAGE_ROOTFS}/boot/bzImage"

	# Remove /boot/runmode
	rm -rf "${IMAGE_ROOTFS}/boot/runmode"
}

recovery_image() {
	install -d ${IMAGE_ROOTFS}/recovery/fonts

	tar -xf "${DEPLOY_DIR_IMAGE}/${SAFEMODE_IMAGE}-${MACHINE}.tar.gz" -C ${IMAGE_ROOTFS}/recovery

	install -m 0644 ${THISDIR}/files/grubenv_non_ni_target	${IMAGE_ROOTFS}/recovery
	install -m 0644 ${THISDIR}/files/unicode.pf2		${IMAGE_ROOTFS}/recovery/fonts

	echo "BUILD_IDENTIFIER=${BUILD_IDENTIFIER}" > ${IMAGE_ROOTFS}/recovery/imageinfo
}

IMAGE_PREPROCESS_COMMAND += "rootfs_update_timestamp;kernel_fixup;recovery_image;"

IMAGE_EFI_BOOT_FILES += " EFI/BOOT/bootx64.efi"

inherit core-image

# useradd and groupadd need to be on sysroot
do_rootfs[depends] += "shadow-native:do_populate_sysroot"

# kernel recipe requires depmodwrapper to populate modules.dep
do_rootfs[depends] += "depmodwrapper-cross:do_populate_sysroot"

do_rootfs[depends] += "virtual/kernel:do_deploy"

do_rootfs[depends] += "${SAFEMODE_IMAGE}:do_image_complete"

symlink_iso () {
	ln -sf ${PN}-${MACHINE}.wic ${DEPLOY_DIR_IMAGE}/${PN}-${MACHINE}.iso
}

ROOTFS_POSTPROCESS_COMMAND += "symlink_iso;"

# Use the same kernel binary as the image to create the wic boot device
WIC_CREATE_EXTRA_ARGS = "-k ${IMAGE_ROOTFS}/boot"
