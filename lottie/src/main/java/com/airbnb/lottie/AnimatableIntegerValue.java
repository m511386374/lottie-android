package com.airbnb.lottie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class AnimatableIntegerValue extends BaseAnimatableValue<Integer, Integer> {
  AnimatableIntegerValue(LottieComposition composition, Integer initialValue) {
    super(composition);
    this.initialValue = initialValue;
  }

  AnimatableIntegerValue(JSONObject json, int frameRate, LottieComposition composition,
      boolean isDp, boolean remap100To255) {
    super(json, frameRate, composition, isDp);
    if (remap100To255) {
      initialValue = initialValue * 255 / 100;
      for (int i = 0; i < keyValues.size(); i++) {
        keyValues.set(i, keyValues.get(i) * 255 / 100);
      }
    }
  }

  @Override protected Integer valueFromObject(Object object, float scale) throws JSONException {
    if (object instanceof Integer) {
      return Math.round((Integer) object * scale);
    } else if (object instanceof JSONArray && ((JSONArray) object).get(0) instanceof Integer) {
      return Math.round(((JSONArray) object).getInt(0) * scale);
    }
    return null;
  }

  @Override public KeyframeAnimation<Integer> createAnimation() {
    if (!hasAnimation()) {
      return new StaticKeyframeAnimation<>(initialValue);
    }

    KeyframeAnimation<Integer> animation =
        new NumberKeyframeAnimation<>(duration, composition, keyTimes, Integer.class, keyValues,
            interpolators);
    animation.setStartDelay(delay);
    return animation;
  }

  public Integer getInitialValue() {
    return initialValue;
  }
}
