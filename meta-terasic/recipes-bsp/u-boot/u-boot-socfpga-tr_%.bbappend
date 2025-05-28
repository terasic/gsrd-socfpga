
DEPENDS:append:titan_s10_som = " arm-trusted-firmware bash u-boot-socfpga-scr"

inherit deploy

do_compile[deptask] = "do_deploy"
