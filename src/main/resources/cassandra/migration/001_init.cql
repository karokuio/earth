CREATE TABLE templates (
  id UUID PRIMARY KEY,
  tag text,
  description text,
  template text,
);

CREATE TABLE deployments (
  id UUID PRIMARY KEY
)
