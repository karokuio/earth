import static ratpack.groovy.Groovy.ratpack
import static earth.util.SystemResources.classpath

ratpack {
    include(classpath('handlers.groovy'))
    include(classpath('bindings.groovy'))
}
