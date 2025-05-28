SUMMARY = "U-boot boot scripts for Intel SoCFPGA devices"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

DEPENDS = "u-boot-mkimage-native dtc-native"

inherit deploy nopackages
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI:titan_s10_som = "file://uboot.txt file://uboot_script.its"

do_configure[noexec] = "1"
do_install[noexec] = "1"

do_compile:titan_s10_som() {
	mkimage -f "${WORKDIR}/uboot_script.its" ${WORKDIR}/boot.scr.uimg
}

do_deploy() {
	install -d ${DEPLOYDIR}
	if [[ "${MACHINE}" == *"titan_s10_som"* ]]; then
		install -m 0755 ${WORKDIR}/uboot.txt ${DEPLOYDIR}/u-boot.txt
		install -m 0644 ${WORKDIR}/boot.scr.uimg ${DEPLOYDIR}/boot.scr.uimg
	else
		:
	fi
}

addtask do_deploy after do_compile before do_build
