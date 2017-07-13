package earth.util

import earth.templates.Template
import org.yaml.snakeyaml.Yaml
import groovy.transform.CompileDynamic

class Templates {

  @CompileDynamic
  static Template parse(String yaml) {
    Yaml parser = new Yaml()
    Map template = parser
      .load(yaml)
      .karoku
      .template

    return new Template(
      tag: template.id,
      type: template.type,
      description: template.description,
      template: yaml,)
  }
}
