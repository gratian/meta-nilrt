DESCRIPTION = "NILRT linux kernel custom configuration build"
LINUX_KERNEL_TYPE = "custom"

inherit kernel
require recipes-kernel/linux/linux-yocto.inc

KERNEL_PACKAGE_NAME:append = "-${LINUX_KERNEL_TYPE}"
KBRANCH = "nilrt/master/6.12"
KCONFIG_MODE="--alldefconfig"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
SRC_URI = " \
	git://github.com/ni/linux.git;protocol=https;branch=${KBRANCH} \
	file://defconfig \
"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
SRCREV ?= "${AUTOREV}"
KERNEL_VERSION_SANITY_SKIP="1"
