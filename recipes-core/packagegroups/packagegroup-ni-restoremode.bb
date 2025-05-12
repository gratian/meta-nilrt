SUMMARY = "initramfs specific packages for NI Linux Realtime distribution"
LICENSE = "MIT"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS:${PN} += "\
	base-passwd \
	bash \
	bzip2 \
	coreutils \
	dosfstools \
	e2fsprogs \
	e2fsprogs-mke2fs \
	e2fsprogs-tune2fs \
	findutils \
	gawk \
	gptfdisk \
	grep \
	init-restore-mode \
	kmod \
	ni-systemreplication \
	parted \
	procps \
	sed \
	sysvinit \
	tar \
	util-linux \
	util-linux-agetty \
	vim-tiny \
"

RDEPENDS:${PN}:x64 += "\
	dmidecode           \
	efibootmgr          \
	efivar              \
	eudev               \
	fw-printenv         \
	grub                \
	grub-editenv        \
	grub-efi            \
	ni-smbios-helper    \
	"

RDEPENDS:${PN}:xilinx-zynq += "\
	mtd-utils           \
	mtd-utils-ubifs     \
	u-boot-fw-utils     \
	"


RRECOMMENDS:${PN}:x64 = "\
	kernel-module-tpm-tis \
	kernel-module-atkbd \
	kernel-module-hyperv-keyboard \
	kernel-module-hv-storvsc \
	kernel-module-hv-vmbus \
	kernel-module-hv-utils \
	kernel-module-hv-balloon \
	kernel-module-i8042 \
"
