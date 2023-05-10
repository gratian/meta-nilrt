SUMMARY = "Set of packages used to create the USB imaging media"
LICENSE = "MIT"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS:${PN} += "\
	base-files \
	base-passwd \
	bash \
	btrfs-tools \
	bzip2 \
	coreutils \
	daemonize \
	dialog \
	dmidecode \
	dpkg-start-stop \
	dosfstools \
	e2fsprogs \
	e2fsprogs-mke2fs \
	e2fsprogs-tune2fs \
	efibootmgr \
	efivar \
	eudev \
	findutils \
	fw-printenv \
	gawk \
	gptfdisk \
	grep \
	grub \
	grub-editenv \
	grub-efi \
	initscripts \
	kernel-modules \
	kmod \
	modutils-initscripts \
	ni-imaging \
	ni-smbios-helper \
	parted \
	pigz \
	procps \
	rsync \
	sed \
	sysvinit \
	tar \
	usbutils \
	util-linux \
	util-linux-agetty \
	vim-tiny \
"
