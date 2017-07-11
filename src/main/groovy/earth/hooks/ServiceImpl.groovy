package earth.hooks

import javax.inject.Inject
import earth.events.Publisher
import ratpack.exec.Promise

class ServiceImpl implements Service {

  @Inject
  Publisher publisher

  Promise<IntegrationType> deploy(IntegrationType type, String json) {
    publisher.publish('deploy.queued.github', json)

    return Promise.value(type)
  }
}
