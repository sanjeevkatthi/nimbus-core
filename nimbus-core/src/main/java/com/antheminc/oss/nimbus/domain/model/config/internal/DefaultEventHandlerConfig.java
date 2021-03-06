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
package com.antheminc.oss.nimbus.domain.model.config.internal;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.collections.MapUtils;

import com.antheminc.oss.nimbus.InvalidConfigException;
import com.antheminc.oss.nimbus.domain.model.config.EventHandlerConfig;
import com.antheminc.oss.nimbus.domain.model.config.event.ConfigEventHandlers.OnParamCreateHandler;
import com.antheminc.oss.nimbus.domain.model.state.event.StateEventHandlers.OnStateChangeHandler;
import com.antheminc.oss.nimbus.domain.model.state.event.StateEventHandlers.OnStateLoadHandler;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Soham Chakravarti
 *
 */
@Getter @Setter
public class DefaultEventHandlerConfig implements EventHandlerConfig {

	// config handlers
	private _InternalConfig<OnParamCreateHandler<Annotation>> onParamCreateHandlers = new _InternalConfig<>();
	
	// state handlers
	private _InternalConfig<OnStateLoadHandler<Annotation>> onStateLoadHandlers = new _InternalConfig<>();
	private _InternalConfig<OnStateChangeHandler<Annotation>> onStateChangeHandlers = new _InternalConfig<>();
	
	
	private static class _InternalConfig<T> {
		private Map<Annotation, T> eventHandlers;
		
		public boolean isEmpty() {
			return MapUtils.isEmpty(eventHandlers);
		}
		
		public Set<Annotation> getAnnotations() {
			return Optional.ofNullable(eventHandlers).map(Map::keySet).orElse(null);	
		}
		
		public void add(Annotation a, T handler) {
			Optional.ofNullable(eventHandlers).orElseGet(()->{
				eventHandlers = new HashMap<>();
				return eventHandlers;
			}).put(a, handler);
		}
		
		public Optional<T> findHandler(Annotation a) {
			return Optional.ofNullable(eventHandlers).map(handlers->handlers.get(a));
		}
		
		public T getHandler(Annotation a) throws InvalidConfigException {
			return findHandler(a).orElseThrow(()->getEx(a));
		}
		
		public InvalidConfigException getEx(Annotation a) {
			return new InvalidConfigException("Expected event handler for annotation: "+a+ " not found");
		}
	}
	
	public boolean isEmpty() {
		return 
			onParamCreateHandlers.isEmpty() && 
			
			onStateLoadHandlers.isEmpty() &&
			onStateChangeHandlers.isEmpty()
		;
 	}
	
	/* onParamCreate */
	@Override
	public Set<Annotation> getOnParamCreateAnnotations() {
		return onParamCreateHandlers.getAnnotations();
	}
	
	@Override
	public Optional<OnParamCreateHandler<Annotation>> findOnParamCreateHandler(Annotation a) {
		return onParamCreateHandlers.findHandler(a);
	}
	
	public void add(Annotation a, OnParamCreateHandler<Annotation> handler) {
		onParamCreateHandlers.add(a, handler);
	}
	
	@Override
	public OnParamCreateHandler<Annotation> getOnParamCreateHandler(Annotation a) throws InvalidConfigException {
		return onParamCreateHandlers.getHandler(a);
	}
	
	/* onStateLoad */
	@Override
	public Set<Annotation> getOnStateLoadAnnotations() {
		return onStateLoadHandlers.getAnnotations();
	}
	
	public void add(Annotation a, OnStateLoadHandler<Annotation> handler) {
		onStateLoadHandlers.add(a, handler);
	}
	
	@Override
	public Optional<OnStateLoadHandler<Annotation>> findOnStateLoadHandler(Annotation a) {
		return onStateLoadHandlers.findHandler(a);
	}
	
	@Override
	public OnStateLoadHandler<Annotation> getOnStateLoadHandler(Annotation a) throws InvalidConfigException {
		return onStateLoadHandlers.getHandler(a);
	}
	
	/* onStateChange */
	@Override
	public Set<Annotation> getOnStateChangeAnnotations() {
		return onStateChangeHandlers.getAnnotations();
	}
	
	public void add(Annotation a, OnStateChangeHandler<Annotation> handler) {
		onStateChangeHandlers.add(a, handler);
	}
	
	@Override
	public Optional<OnStateChangeHandler<Annotation>> findOnStateChangeHandler(Annotation a) {
		return onStateChangeHandlers.findHandler(a);
	}
	
	@Override
	public OnStateChangeHandler<Annotation> getOnStateChangeHandler(Annotation a) throws InvalidConfigException {
		return onStateChangeHandlers.getHandler(a);
	}
}
