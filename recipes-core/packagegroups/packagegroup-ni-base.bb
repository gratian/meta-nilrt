# (C) Copyright 2013,
#  National Instruments Corporation.
#  All rights reserved.

SUMMARY = "Base set of packages for NI Linux Realtime distribution"
LICENSE = "MIT"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

#
# Set by the machine configuration with packages essential for device bootup
#
MACHINE_ESSENTIAL_EXTRA_RDEPENDS ?= ""
MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS ?= ""

ALL_DISTRO_ARM_PACKAGES = "\
	mtd-utils \
	mtd-utils-ubifs \
	u-boot-fw-utils \
	jitterentropy-rngd \
"

ALL_DISTRO_x64_PACKAGES = "\
	linux-firmware-i915 \
	dmidecode \
	efibootmgr \
	efivar \
	fw-printenv \
	e2fsprogs \
	e2fsprogs-mke2fs \
	pstore-save \
"

RDEPENDS:${PN} = "\
	${MACHINE_ESSENTIAL_EXTRA_RDEPENDS} \
	${VIRTUAL-RUNTIME_mountpoint} \
"

RDEPENDS:${PN} += "\
	${@bb.utils.contains('COMBINED_FEATURES', 'pci', 'pciutils-ids', '',d)} \
	${@bb.utils.contains('MACHINE_FEATURES', 'acpi', 'busybox-acpid', '', d)} \
	${@bb.utils.contains('MACHINE_FEATURES', 'keyboard', 'keymaps', '', d)} \
	avahi-daemon \
	base-files \
	base-files-nilrt \
	base-passwd \
	busybox \
	busybox-ifplugd \
	busybox-udhcpd \
	busybox-zcip \
	coreutils-hostname \
	crio-support-scripts \
	cronie \
	curl \
	daemonize \
	dpkg-start-stop \
	ethtool \
	eudev \
	glibc-gconv-utf-16 \
	gptfdisk \
	init-ifupdown \
	initscripts \
	initscripts-nilrt \
	iproute2 \
	iptables \
	kernel-modules \
	kmod \
	libavahi-client \
	libavahi-common \
	libavahi-core \
	libcap-bin\
	libnss-mdns \
	libpam \
	librtpi \
	libstdc++ \
	logrotate \
	lsbinitscripts \
	modutils-initscripts \
	netbase \
	ni-hw-scripts \
	ni-rtfeatures \
	ni-safemode-utils \
	ni-shutdown-guard \
	ni-systemformat \
	ni-utils \
	niacctbase \
	niwatchdogpet \
	openssh-scp \
	openssh-sftp-server \
	openssh-ssh \
	openssh-sshd \
	openvpn \
	opkg \
	opkg-keyrings \
	os-release \
	pigz \
	run-postinsts \
	sudo \
	sysconfig-settings \
	sysconfig-settings-console \
	syslog-ng \
	sysvinit \
	tar \
	udev-extraconf \
	usbutils \
	util-linux-agetty \
	util-linux-hwclock \
	util-linux-mount \
	util-linux-runuser \
	util-linux-umount \
	${@bb.utils.contains('TARGET_ARCH', 'arm', \
		'${ALL_DISTRO_ARM_PACKAGES}', \
		'${ALL_DISTRO_x64_PACKAGES}', d)} \
"
