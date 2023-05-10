SUMMARY = "NILRT USB imaging scripts"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PV = "1.0"

SRC_URI = "\
	file://mmc_storage_device_codes.allow \
	file://nilrt \
	file://nilrt-disk \
	file://nilrt-image \
	file://nilrt-image-list \
	file://nilrt-mount \
	file://nilrt-partition \
	file://nilrt-sync \
"

DEPENDS += "update-rc.d-native"
RDEPENDS:${PN} += "bash dialog"

do_install() {
	install -d ${D}${sysconfdir}/nilrt
	install -m 0644 ${WORKDIR}/mmc_storage_device_codes.allow ${D}${sysconfdir}/nilrt/

	install -d ${D}${bindir}
	install -m 0755 ${WORKDIR}/nilrt ${D}${bindir}/nilrt
	install -m 0755 ${WORKDIR}/nilrt-disk ${D}${bindir}/nilrt-disk
	install -m 0755 ${WORKDIR}/nilrt-image ${D}${bindir}/nilrt-image
	install -m 0755 ${WORKDIR}/nilrt-image-list ${D}${bindir}/nilrt-image-list
	install -m 0755 ${WORKDIR}/nilrt-mount ${D}${bindir}/nilrt-mount
	install -m 0755 ${WORKDIR}/nilrt-partition ${D}${bindir}/nilrt-partition
	install -m 0755 ${WORKDIR}/nilrt-sync ${D}${bindir}/nilrt-sync
}

pkg_postinst_ontarget:${PN} () {
# Find drive corresponding to the current rootfs
DRIVE=$(findmnt -n -o SOURCE / | sed 's/[0-9]*$//')
echo "nilrt imaging: using $DRIVE for storing images"
echo "nilrt imaging: creating btrfs images partition"

# Add a third partition formatted btrfs for storing images
mkdir -p /images > /dev/null 2>&1
echo "$DRIVE""3 /images btrfs defaults,compress=zstd 0 0" >> /etc/fstab
echo "n
p
3


w" | fdisk "$DRIVE" > /dev/null 2>&1
mkfs.btrfs -f "$DRIVE""3" -L "imagefs" > /dev/null 2>&1

# Mount the newly created partition
mount /images > /dev/null 2>&1
}
