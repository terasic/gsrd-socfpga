SUMMARY = "Intel SoCFPGA Golden Hardware Reference Design (GHRD)"
DESCRIPTION = "Prebuilt FPGA bitstream for SOC Development Kit"
SECTION = "bsp"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

inherit deploy

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Proprietary;md5=0557f9d92cf58f2ccdd50f62f8ac0b28"

IMAGE_TYPE ?= "gsrd"

RBO_RELEASE_VER ?= "2024.11"
GHRD_REPO ?= "https://releases.rocketboards.org/release/${RBO_RELEASE_VER}/rbf-source"

ARM64_GHRD_CORE_RBF = "golden_top_hps.core.rbf"

SRC_URI:titan_s10_som ?= "\
		file://${MACHINE}/${ARM64_GHRD_CORE_RBF};name=${MACHINE}_core; \
		"

SRC_URI[titan_s10_som_core.sha256sum] = "525188d8c809714223f7acee5ad25e3a2afc3a24fc651b69ca1f3f41f92a6b7a"

S ?= "${WORKDIR}/${MACHINE}"

PROVIDES = "virtual/bitstream"

FILES:${PN} = " \
		/boot \
		/usr/lib/firmware \
		"

PACKAGES = "${PN}"

PACKAGE_ARCH = "${MACHINE_ARCH}"

do_install () {
	if ${@bb.utils.contains("MACHINE", "titan_s10_som", "true", "false", d)}; then
		install -D -m 0644 ${WORKDIR}/${MACHINE}/${ARM64_GHRD_CORE_RBF} ${D}/boot/${ARM64_GHRD_CORE_RBF}
	fi	
}

do_deploy () {
	if ${@bb.utils.contains("MACHINE", "titan_s10_som", "true", "false", d)}; then
		install -D -m 0644 ${WORKDIR}/${MACHINE}/${ARM64_GHRD_CORE_RBF} ${DEPLOYDIR}/${MACHINE}_rbf/${ARM64_GHRD_CORE_RBF}
	fi
}

addtask install after do_configure before do_deploy
addtask deploy after do_install
