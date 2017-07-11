package earth.hooks

import static ratpack.jackson.Jackson.json

import ratpack.http.TypedData
import ratpack.exec.Promise
import ratpack.func.Action
import ratpack.handling.Context
import java.util.function.Predicate

class Handlers {

  static void deploy(final Context ctx) {
    Service service = ctx.get(Service)

    resolveIntegrationType(ctx)
      .flatMap { IntegrationType type ->
        ctx.request.body.flatMap { TypedData data ->
          service.deploy(type, data.text)
        }
      }.then { IntegrationType type ->
        ctx.render(json(type: type))
      }
  }

  static Promise<IntegrationType> resolveIntegrationType(Context ctx) {
    def github = [ctx.header('X-GitHub-Event'),
                  ctx.header('X-Hub-Signature'),
                  ctx.header('X-GitHub-Delivery')]
    def gitlab = [ctx.header('X-Gitlab-Event')]

    if (github.any { it.isPresent() }) {
      return Promise.value(IntegrationType.GITHUB)
    }

    if (gitlab.any { it.isPresent() }) {
      return Promise.value(IntegrationType.GITLAB)
    }

    return Promise.value(IntegrationType.UNKNOWN)
  }
}
