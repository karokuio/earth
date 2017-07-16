package earth.hooks

import javax.inject.Inject
import earth.events.Events
import earth.events.Publisher
import ratpack.exec.Promise

class ServiceImpl implements Service {

  @Inject
  Publisher publisher

  @Override
  Promise<IntegrationType> deploy(IntegrationType type, String json) {
    publisher.publish(Events.WHOOK_GITHUB_REQUESTED, json)

    return Promise.value(type)
  }
}
