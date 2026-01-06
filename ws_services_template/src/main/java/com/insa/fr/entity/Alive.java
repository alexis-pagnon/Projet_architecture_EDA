package com.insa.fr.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 *
 * @author herve
 */

@Data

@NoArgsConstructor
@AllArgsConstructor

@JsonPropertyOrder({"alive","version","whoami"})
public class Alive 
{
String alive;
String version;
String whoami;  
  
}
