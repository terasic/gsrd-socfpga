HOMEPAGE = "http://www.denx.de/wiki/U-Boot/WebHome"
SECTION = "bootloaders"

DEPENDS = "coreutils-native bc-native bison-native dtc-native flex-native"
DEPENDS:append = " python3-setuptools-native swig-native u-boot-mkimage-native"
DEPENDS:append = " u-boot-tools"

DEPENDS:append:comet_a65 = " arm-trusted-firmware bash"

COMPILE_PREPEND_FILES:comet_a65 = " bl31.bin "

DEPLOY_APPEND_FILES:comet_a65 = ""


LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM ?= "file://Licenses/README;md5=2ca5f2c35c8cc335f0a19756634782f1"
PE = "1"

UBOOT_REPO ?= "git://github.com/altera-fpga/u-boot-socfpga.git"
UBOOT_PROT ?= "https"

UBOOT_VERSION_PREFIX ?= "socfpga_"
UBOOT_VERSION ?= "v2024.04"
UBOOT_BRANCH ?= "${UBOOT_VERSION_PREFIX}${UBOOT_VERSION}"

SRCREV ?= "${AUTOREV}"
PV:append = "+git${SRCPV}"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

RPROVIDES:${PN} = "u-boot"

SRC_URI = "${UBOOT_REPO};protocol=${UBOOT_PROT};branch=${UBOOT_BRANCH}"

do_compile:prepend() {
	if [ -n "${COMPILE_PREPEND_FILES}" ]; then
		for file in ${COMPILE_PREPEND_FILES}; do
			cp ${DEPLOY_DIR_IMAGE}/${file} ${B}/${config}/${file}
			cp ${DEPLOY_DIR_IMAGE}/${file} ${S}/${file}
		done
	fi
}

do_install:append() {
	if [ "${MACHINE}" = *"comet_a65"* ] ]; then
		cp ${B}/u-boot.itb ${B}/u-boot-${UBOOT_CONFIG}.itb
		install -D -m 644 ${B}/u-boot-${config}.itb ${D}/boot/u-boot-${UBOOT_CONFIG}-${PV}-${PR}.itb
		ln -sf u-boot-${UBOOT_CONFIG}-${PV}-${PR}.itb ${D}/boot/u-boot.itb-${UBOOT_CONFIG}
		ln -sf u-boot-${UBOOT_CONFIG}-${PV}-${PR}.itb ${D}/boot/u-boot.itb
		rm -rf  ${D}/boot/?*.img*

	fi
}

do_deploy:append() {
	if [ -n "${DEPLOY_APPEND_FILES}" ]; then
		for file in ${DEPLOY_APPEND_FILES}; do
			install -m 744 ${B}/${config}/${file} ${DEPLOYDIR}/${file}
		done
	fi
}
require ${COREBASE}/meta/recipes-bsp/u-boot/u-boot.inc
