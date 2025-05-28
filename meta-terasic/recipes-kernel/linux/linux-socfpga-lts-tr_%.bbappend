# Append GSRD SoCFPGA device tree source include files
# As this is custom to Intel SoCFPGA GSRD, hence it is not suitable to be upstreamed to meta-intel-fpga

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

DEPENDS = "u-boot-mkimage-native dtc-native"

SRC_URI:append:titan_s10_som = " file://fit_kernel_${MACHINE}.its \
								 file://${MACHINE}.config"

inherit deploy

LINUXDEPLOYDIR = "${WORKDIR}/deploy-${PN}"
DTBDEPLOYDIR = "${DEPLOY_DIR_IMAGE}/devicetree"


do_configure:prepend() {
	if [[ "${MACHINE}" == *"titan_s10_som"* ]]; then
		# use titan-s10-som.config
		install -m 0644 ${WORKDIR}/${MACHINE}.config ${B}/.config
	fi
}

do_deploy:append() {
	# Stage required binaries for kernel.itb

	if [[ "${MACHINE}" == *"titan_s10_som"* ]]; then
		# linux.dtb
		cp ${DTBDEPLOYDIR}/socfpga_stratix10_${MACHINE}.dtb ${B}
		# core.rbf
		cp ${DEPLOY_DIR_IMAGE}/${MACHINE}_rbf/golden_top_hps.core.rbf ${B}

	fi

	# Generate and deploy kernel.itb
	if [[ "${MACHINE}" == *"titan_s10_som"* ]]; then
		# kernel.its
		cp ${WORKDIR}/fit_kernel_${MACHINE}.its ${B}
		# Image
		cp ${LINUXDEPLOYDIR}/Image ${B}
		# Compress Image to lzma format
		xz --format=lzma ${B}/Image
		# Generate kernel.itb
		mkimage -f ${B}/fit_kernel_${MACHINE}.its ${B}/kernel.itb
		# Deploy kernel.its, kernel.itb and Image.lzma
		install -m 744 ${B}/fit_kernel_${MACHINE}.its ${DEPLOYDIR}
		install -m 744 ${B}/kernel.itb ${DEPLOYDIR}
		install -m 744 ${B}/Image.lzma ${DEPLOYDIR}
	fi
}
