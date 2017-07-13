import static ratpack.groovy.Groovy.ratpack

import earth.Config
import earth.templates.Handlers as TEMPLATES
import earth.hooks.Handlers as HOOKS
import earth.events.Handlers as EVENTS
import earth.proxy.Handlers as PROXY
import ratpack.http.MutableHeaders
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
      all {
        MutableHeaders headers = response.headers
        headers.set('Access-Control-Allow-Origin', '*')
        headers.set('Access-Control-Allow-Headers', 'x-requested-with, origin, content-type, accept')
        next()
      }
      get('events', EVENTS.&all)
      prefix(/::^docker.*/) {
        all(PROXY.&proxy)
      }
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
