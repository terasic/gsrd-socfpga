SUMMARY = "U-boot boot scripts for Intel SoCFPGA devices"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

DEPENDS = "u-boot-mkimage-native dtc-native"

inherit deploy nopackages
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI:comet_a65 = "file://comet_a65_uboot.txt \
					file://comet_a65_uboot_script.its \
					"
S = "${WORKDIR}/sources-unpack"

do_configure[noexec] = "1"
do_install[noexec] = "1"

do_compile:comet_a65() {
	mkimage -f "${WORKDIR}/sources-unpack/comet_a65_uboot_script.its" ${WORKDIR}/sources-unpack/boot.scr.uimg

}

do_deploy() {
	install -d ${DEPLOYDIR}

	if [[ "${MACHINE}" == *"comet_a65"* ]]; then
		install -m 0755 ${WORKDIR}/sources-unpack/comet_a65_uboot.txt ${DEPLOYDIR}/comet_a65_uboot.txt
		install -m 0644 ${WORKDIR}/sources-unpack/boot.scr.uimg ${DEPLOYDIR}/boot.scr.uimg
		
	else
		:
	fi
}

addtask do_deploy after do_compile before do_build
