FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

DEPENDS:append:comet_a65 = " arm-trusted-firmware bash u-boot-socfpga-scr"

inherit deploy

do_compile[deptask] = "do_deploy"
