SUMMARY = "Intel SoCFPGA Development Kit devicetrees"
DESCRIPTION = "Devicetree addons for Intel SoCFPGA Development Kit examples"
SECTION = "bsp"

LICENSE = "MIT & GPL-2.0-only"

KERNEL_INCLUDE = " \
        ${STAGING_KERNEL_DIR}/arch/${ARCH}/boot/dts \
        ${STAGING_KERNEL_DIR}/arch/${ARCH}/boot/dts/* \
        ${STAGING_KERNEL_DIR}/scripts/dtc/include-prefixes \
        "
inherit devicetree

PROVIDES = "virtual/dtb"

COMPATIBLE_MACHINE = "(titan_s10_som)"


do_configure[depends] += "virtual/kernel:do_configure"


do_configure:append:titan_s10_som() {
	cp ${STAGING_KERNEL_DIR}/arch/${ARCH}/boot/dts/altera/socfpga_stratix10.dtsi ${WORKDIR}

	# GSRD DTB Generation
	# MMC, QSPI
	cp ${STAGING_KERNEL_DIR}/arch/${ARCH}/boot/dts/altera/socfpga_stratix10_titan_s10_som.dts ${WORKDIR}
}


