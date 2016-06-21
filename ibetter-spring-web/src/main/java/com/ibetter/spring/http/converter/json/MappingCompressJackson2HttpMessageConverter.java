/*
 * Copyright 2002-2014 the original author or authors.
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

package com.ibetter.spring.http.converter.json;

import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibetter.spring.http.converter.wrapper.ConvertWrapper;

public class MappingCompressJackson2HttpMessageConverter extends AbstractJackson2HttpMessageConverter {

	private ConvertWrapper convertWrapper;

	public MappingCompressJackson2HttpMessageConverter() {
		this(Jackson2ObjectMapperBuilder.json().build());
	}
	public Boolean isSupportCompress=true;
	public MappingCompressJackson2HttpMessageConverter(ObjectMapper objectMapper) {
		super(objectMapper, new MediaType("application", "json", DEFAULT_CHARSET), new MediaType("application", "*+json", DEFAULT_CHARSET));
	}
	
	@Override
	public OutputStream getWrappOutputStream(OutputStream out) {
		if(isSupportCompress){
			return new DeflaterOutputStream(out);
		}
		return out;
	}

	public ConvertWrapper getConvertWrapper() {
		return convertWrapper;
	}

	public void setConvertWrapper(ConvertWrapper convertWrapper) {
		this.convertWrapper = convertWrapper;
	}

	@Override
	public Object enhanceValue(Object value) {
		if(convertWrapper!=null){
			return convertWrapper.wrap(value);
		}
		return super.enhanceValue(value);
	}

	public Boolean getIsSupportCompress() {
		return isSupportCompress;
	}

	public void setIsSupportCompress(Boolean isSupportCompress) {
		this.isSupportCompress = isSupportCompress;
	}

	
}
