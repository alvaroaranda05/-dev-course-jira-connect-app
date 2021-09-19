package com.deiser.jira.connect.service.configuration;

import com.deiser.jira.connect.model.Configuration;
import com.deiser.jira.connect.model.ConfigurationEntity;
import com.deiser.jira.connect.repository.database.ConfigurationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class ConfigurationServiceImplTest {

    private static final String CONFIG_VALUE = "VALUE";
    private static final String CONFIG_KEY = "KEY";
    private static final String CLIENT_KEY = "CLIENTKEY";

    @Mock
    private ConfigurationRepository configurationRepository;
    @Mock
    private ConfigurationEntity entity;

    private ConfigurationServiceImpl sut;

    @BeforeEach
    void setUp(){
        initMocks(this);

        when(entity.getValue()).thenReturn(CONFIG_VALUE);
        when(entity.getKey()).thenReturn(CONFIG_KEY);

        sut = new ConfigurationServiceImpl(configurationRepository);
    }

    @Test
    void get_withConfigurations_shouldReturnConfigurations(){
        //Given
        List<ConfigurationEntity> configurationList = Collections.singletonList(entity);
        when(configurationRepository.findAllByClientKey(CLIENT_KEY)).thenReturn(configurationList);

        //When
        List<Configuration> result = sut.get(CLIENT_KEY);

        //Then
        assertThat(result)
                .isNotNull()
                .hasSize(1)
                .element(0)
                .hasFieldOrPropertyWithValue("value", CONFIG_VALUE)
                .hasFieldOrPropertyWithValue("key", CONFIG_KEY);
        verify(configurationRepository).findAllByClientKey(CLIENT_KEY);
        verifyNoMoreInteractions(configurationRepository);
    }

    @Test
    void get_withoutConfiguration_shouldReturnEmptyList() {
        //Given
        List<ConfigurationEntity> configurationList = Collections.emptyList();
        when(configurationRepository.findAllByClientKey(CLIENT_KEY)).thenReturn(configurationList);

        //When
        List<Configuration> result = sut.get(CLIENT_KEY);

        //Then
        assertThat(result)
                .isNotNull()
                .isEmpty();
        verify(configurationRepository).findAllByClientKey(CLIENT_KEY);
        verifyNoMoreInteractions(configurationRepository);
    }

    @Test
    void getByKey_withExistedConfiguration_shouldReturnIt(){
        //Given
        when(configurationRepository.existsByKeyAndClientKey(anyString(), eq(CLIENT_KEY))).thenReturn(true);
        when(configurationRepository.findByKeyAndClientKey(anyString(), eq(CLIENT_KEY))).thenReturn(entity);

        //When
        Configuration result = sut.get(CONFIG_KEY, CLIENT_KEY);

        //Then
        assertThat(result)
                .isNotNull()
                .isInstanceOf(Configuration.class);
        assertThat(result.getValue()).isEqualTo(CONFIG_VALUE);
        assertThat(result.getKey()).isEqualTo(CONFIG_KEY);

        verify(configurationRepository).existsByKeyAndClientKey(anyString(), eq(CLIENT_KEY));
        verify(configurationRepository).findByKeyAndClientKey(anyString(), eq(CLIENT_KEY));
        verifyNoMoreInteractions(configurationRepository);
    }

    @Test
    void getByKey_withNoExistedConfiguration_shouldReturnNull(){
        //Given
        when(configurationRepository.existsByKeyAndClientKey(anyString(), eq(CLIENT_KEY))).thenReturn(false);

        //When
        Configuration result = sut.get(CONFIG_KEY, CLIENT_KEY);

        //Then
        assertThat(result)
                .isNull();

        verify(configurationRepository).existsByKeyAndClientKey(anyString(), eq(CLIENT_KEY));
        verifyNoMoreInteractions(configurationRepository);
    }

    @Test
    void exist_shouldCallExistKeyMethodOfManager(){
        //Given
        when(configurationRepository.existsByKeyAndClientKey(anyString(), eq(CLIENT_KEY))).thenReturn(true);

        //When
        boolean result = sut.exist(CONFIG_KEY, CLIENT_KEY);

        //Then
        assertThat(result).isTrue();

        verify(configurationRepository).existsByKeyAndClientKey(anyString(), eq(CLIENT_KEY));
        verifyNoMoreInteractions(configurationRepository);
    }

    @Test
    void create_withExistedValue_shouldReturnNull(){
        //Given
        when(configurationRepository.existsByKeyAndClientKey(anyString(), eq(CLIENT_KEY))).thenReturn(true);

        //When
        Configuration result = sut.create(CONFIG_KEY, CONFIG_VALUE, CLIENT_KEY);

        //Then
        assertThat(result).isNull();

        verify(configurationRepository).existsByKeyAndClientKey(anyString(), eq(CLIENT_KEY));
        verifyNoMoreInteractions(configurationRepository);
    }

    @Test
    void create_withNoExistedValue_shouldCreateAndReturn(){
        //Given
        when(configurationRepository.existsByKeyAndClientKey(anyString(), eq(CLIENT_KEY))).thenReturn(false);
        when(configurationRepository.save(any(ConfigurationEntity.class))).thenReturn(entity);

        //When
        Configuration result = sut.create(CONFIG_KEY, CONFIG_VALUE, CLIENT_KEY);

        //Then
        assertThat(result).isNotNull();
        assertThat(result.getKey()).isEqualTo(CONFIG_KEY);
        assertThat(result.getValue()).isEqualTo(CONFIG_VALUE);

        verify(configurationRepository).existsByKeyAndClientKey(anyString(), eq(CLIENT_KEY));
        verify(configurationRepository).save(any(ConfigurationEntity.class));
        verifyNoMoreInteractions(configurationRepository);
    }

    @Test
    void update_withNoExistedValue_shouldReturnNullAndNotSave(){
        //Given
        when(configurationRepository.findByKeyAndClientKey(anyString(), eq(CLIENT_KEY))).thenReturn(null);

        //When
        Configuration result = sut.update(CONFIG_KEY, CONFIG_VALUE, CLIENT_KEY);

        //Then
        assertThat(result).isNull();

        verify(configurationRepository).findByKeyAndClientKey(anyString(), eq(CLIENT_KEY));
        verifyNoMoreInteractions(configurationRepository);
    }

    @Test
    void update_withExistedValue_shouldUpdateThePreviousValue(){
        //Given
        when(configurationRepository.findByKeyAndClientKey(anyString(), eq(CLIENT_KEY))).thenReturn(entity);
        doNothing().when(entity).setValue(anyString());
        when(configurationRepository.save(any(ConfigurationEntity.class))).thenReturn(entity);

        //When
        Configuration result = sut.update(CONFIG_KEY, CONFIG_VALUE, CLIENT_KEY);

        //Then
        assertThat(result).isNotNull();

        verify(configurationRepository).findByKeyAndClientKey(anyString(), eq(CLIENT_KEY));
        verify(configurationRepository).save(any(ConfigurationEntity.class));
        verifyNoMoreInteractions(configurationRepository);
    }

    @Test
    void delete_withNoExistedValue_shouldReturnFalseAndNotDelete(){
        //Given
        when(configurationRepository.findByKeyAndClientKey(anyString(), eq(CLIENT_KEY))).thenReturn(null);

        //When
        boolean result = sut.delete(CONFIG_KEY, CLIENT_KEY);

        //Then
        assertThat(result).isFalse();

        verify(configurationRepository).findByKeyAndClientKey(anyString(), eq(CLIENT_KEY));
        verifyNoMoreInteractions(configurationRepository);
    }

    @Test
    void delete_withExistedValue_shouldReturnTrueAndDeleteTheValue(){
        //Given
        when(configurationRepository.findByKeyAndClientKey(anyString(), eq(CLIENT_KEY))).thenReturn(entity);
        doNothing().when(configurationRepository).delete(entity);

        //When
        boolean result = sut.delete(CONFIG_KEY, CLIENT_KEY);

        //Then
        assertThat(result).isTrue();
        ArgumentCaptor<ConfigurationEntity> entityArgumentCaptor = ArgumentCaptor.forClass(ConfigurationEntity.class);

        verify(configurationRepository).findByKeyAndClientKey(anyString(), eq(CLIENT_KEY));
        verify(configurationRepository).delete(entityArgumentCaptor.capture());
        verifyNoMoreInteractions(configurationRepository);

        assertThat(entityArgumentCaptor.getValue().getValue()).isEqualTo(CONFIG_VALUE);
        assertThat(entityArgumentCaptor.getValue().getKey()).isEqualTo(CONFIG_KEY);
    }
}