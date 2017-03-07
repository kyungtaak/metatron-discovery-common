package com.skt.metatron.discovery.common.preparation;

import com.skt.metatron.discovery.common.preparation.rule.Rule;

public interface Parser {
  Rule parse(String code);
}
