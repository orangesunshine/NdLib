package com.orange.lib.common.config;

import com.orange.lib.common.image.IImage;
import com.orange.lib.component.toast.IToast;

public interface IBuildFactory {
    IToast buildToast();

    IImage buildImage();
}
