/**
 *  Copyright 2016-2018 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.antheminc.oss.nimbus.context;

import java.util.Collection;

import com.antheminc.oss.nimbus.InvalidConfigException;

/**
 * @author Soham Chakravarti
 *
 */
public interface BeanResolverStrategy {

	public <T> T find(Class<T> type);
	
	public <T> T get(Class<T> type) throws InvalidConfigException;
	
	
	public <T> T find(Class<T> type, String qualifier);
	
	public <T> T get(Class<T> type, String qualifier) throws InvalidConfigException;
	
	public <T> T find(Class<T> type, Class<?>...generics);
	public <T> T get(Class<T> type, Class<?>...generics) throws InvalidConfigException;
	
	public <T> Collection<T> findMultiple(Class<T> type);
	public <T> Collection<T> getMultiple(Class<T> type);
}
