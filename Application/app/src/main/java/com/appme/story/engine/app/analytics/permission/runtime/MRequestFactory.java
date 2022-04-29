/*
 * Copyright 2018 Zhenjie Yan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.appme.story.engine.app.analytics.permission.runtime;

import com.appme.story.engine.app.analytics.permission.source.Source;

/**
 * Created by Zhenjie Yan on 2018/4/28.
 */
public class MRequestFactory implements Runtime.PermissionRequestFactory {

    @Override
    public PermissionRequest create(Source source) {
        return new MRequest(source);
    }
}
