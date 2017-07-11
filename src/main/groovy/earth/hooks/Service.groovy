package earth.hooks

import ratpack.exec.Promise

interface Service {

  Promise<IntegrationType> deploy(IntegrationType type, String json)
}
