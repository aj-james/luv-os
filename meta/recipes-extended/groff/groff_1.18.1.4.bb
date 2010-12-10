DESCRIPTION = "groff-a short reference for the GNU roff language "
SECTION = "console/utils"
PRIORITY = "required"
HOMEPAGE = "ftp://ftp.gnu.org/gnu/groff/"
LICENSE = "GPLv2"
PR = "r1"

LIC_FILES_CHKSUM = "file://COPYING;md5=e43fc16fccd8519fba405f0a0ff6e8a3"

SRC_URI = "ftp://ftp.gnu.org/gnu/groff/groff-${PV}.tar.gz \
          file://groff-1.18.1.4-remove-mom.patch;striplevel=1 \
          file://man-local.patch \
          file://mdoc-local.patch" 

inherit autotools

EXTRA_OECONF="--without-x --prefix=${D} --exec-prefix=${D} --bindir=${D}${bindir} --datadir=${D}${datadir} --mandir=${D}${datadir}/man --infodir=${D}${datadir}info --with-appresdir=${D}${datadir}"

SRC_URI[md5sum] = "ceecb81533936d251ed015f40e5f7287"
SRC_URI[sha256sum] = "ff3c7c3b6cae5e8cc5062a144de5eff0022e8e970e1774529cc2d5dde46ce50d"
PARALLEL_MAKE = ""


do_configure (){
	oe_runconf
}

do_install_prepend() {
  install -m 0755 -d ${D}
}


do_install_append() {
     mkdir -p ${D}${sysconfdir}/groff
     cp -rf ${D}${datadir}/groff/site-tmac/* ${D}${sysconfdir}/groff/
     cp -rf ${D}${datadir}/groff/site-tmac/* ${D}${datadir}/groff/${PV}/tmac/
}

pkg_postinst_${PN}() {
    ln -s ${bindir}/tbl ${bindir}/gtbl
    echo "export GROFF_FONT_PATH=/usr/share/groff/${PV}/font" >> ${sysconfdir}/profile
    echo "export GROFF_TMAC_PATH=/usr/share/groff/${PV}/tmac" >> ${sysconfdir}/profile
}

