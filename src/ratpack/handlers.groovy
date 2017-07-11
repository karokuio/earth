import static ratpack.groovy.Groovy.ratpack

import earth.Config
import earth.templates.Handlers as TEMPLATES
import earth.hooks.Handlers as HOOKS
import ratpack.server.ServerConfigBuilder

ratpack {
  serverConfig { ServerConfigBuilder config ->
    config
    .port(8080)
    .yaml("earth.yml")
    .require("", Config)
  }

  handlers {
    prefix('api/v1') {
      path('templates') {
        byMethod {
          get(TEMPLATES.&list)
          post(TEMPLATES.&insert)
          delete(TEMPLATES.&delete)
        }
      }
      path('hooks') {
        byMethod {
          post(HOOKS.&deploy)
        }
      }
    }
  }
}
