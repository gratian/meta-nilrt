SUMMARY = "NI cgroup setup for LabVIEW Real Time"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

RDEPENDS:${PN} = "bash opkg update-rc.d"

inherit update-rc.d systemd

SRC_URI = "file://ni-cgroups \
	file://ni-cgroups-v1 \
	file://ni-cgroups-v2 \
	file://ni-cgroups.service \
	"

INITSCRIPT_NAME = "ni-cgroups"
INITSCRIPT_PARAMS = "start 01 4 5 ."
SYSTEMD_SERVICE:${PN} = "ni-cgroups.service"

S = "${WORKDIR}"

do_install () {
    if ${@bb.utils.contains('DISTRO_FEATURES','sysvinit','true','false',d)}; then
	install -d ${D}${sysconfdir}/init.d/
	install -Dm 0755 ${WORKDIR}/ni-cgroups ${D}${sysconfdir}/init.d/
	install -Dm 0755 ${WORKDIR}/ni-cgroups-v1 ${D}${sysconfdir}/init.d/
	install -Dm 0755 ${WORKDIR}/ni-cgroups-v2 ${D}${sysconfdir}/init.d/
    fi

    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
	# systemd requires cgroups v2
	install -Dm 0755 ${WORKDIR}/ni-cgroups-v2 ${D}${sbindir}
	install -Dm 0644 ${WORKDIR}/ni-cgroups.service ${D}${systemd_system_unitdir}
	sed -i -e "s,@sbindir@,${sbindir},g" ${D}${systemd_system_unitdir}/ni-cgroups.service
    fi
}
