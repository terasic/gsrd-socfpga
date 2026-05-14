# Append GSRD SoCFPGA device tree source include files
# As this is custom to Intel SoCFPGA GSRD, hence it is not suitable to be upstreamed to meta-intel-fpga

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

DEPENDS = "u-boot-mkimage-native dtc-native"

SRC_URI:append:comet_a65 = " file://${MACHINE}.config"

inherit deploy

do_configure:prepend() {
    if [ "${MACHINE}" = "comet_a65" ]; then
        CONFIG_SOURCE=""
        if [ -f "${WORKDIR}/sources-unpack/${MACHINE}.config" ]; then
            CONFIG_SOURCE="${WORKDIR}/sources-unpack/${MACHINE}.config"
        fi
        
        if [ -n "${CONFIG_SOURCE}" ]; then
            mkdir -p ${B}
            install -m 0644 ${CONFIG_SOURCE} ${B}/.config
        fi
    fi
}

