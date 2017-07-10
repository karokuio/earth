import earth.data.DataModule
import earth.init.InitModule
import earth.templates.Module as TEMPLATES

import static ratpack.groovy.Groovy.ratpack

ratpack {
    bindings {
        module DataModule
        module InitModule
        module TEMPLATES
    }
}
