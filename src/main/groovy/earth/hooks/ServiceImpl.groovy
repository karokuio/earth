package earth.hooks

import javax.inject.Inject
import earth.events.Notifier
import ratpack.exec.Promise

class ServiceImpl implements Service {

  @Inject
  Notifier notifier

  Promise<IntegrationType> deploy(IntegrationType type, String json) {
    notifier.event('deploy.queued.github', json)

    return Promise.value(type)
  }
}
