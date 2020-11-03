package com.example.Pokedex.mappers;

import com.example.Pokedex.dto.ItemDto;
import com.example.Pokedex.entities.Item;
import org.mapstruct.Mapper;

/*
        <description>
        @author Marcus Sandberg
        @since 2020-11-03
*/

@Mapper
public interface ItemMapper { Item itemDtoToItem(ItemDto itemDto); }
