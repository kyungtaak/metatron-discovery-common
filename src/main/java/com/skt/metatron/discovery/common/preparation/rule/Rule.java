package com.skt.metatron.discovery.common.preparation.rule;

import java.util.function.Supplier;

/**
 * Created by kyungtaak on 2017. 3. 3..
 */
public interface Rule {

  String getName();

  interface Factory extends Supplier<Rule> {
  }

  interface Library {
  }
}
