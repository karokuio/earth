import earth.data.DataModule
import earth.init.InitModule
import earth.templates.Module as TEMPLATES
import earth.notifiers.Module as NOTIFIERS
import earth.storage.Module as STORAGE
import earth.hooks.Module as HOOKS

import static ratpack.groovy.Groovy.ratpack

ratpack {
    bindings {
        module DataModule
        module InitModule
        module TEMPLATES
        module NOTIFIERS
        module STORAGE
        module HOOKS
    }
}
