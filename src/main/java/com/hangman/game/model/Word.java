package com.hangman.game.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "word")
public class Word {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "word_id")
	private int id;

	@Column(name = "word")
	private String word;

	@Column(name = "description")
	private String description;
}
