package com.hangman.game.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "room")
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "room_id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "creator")
	private int creator;

	@Column(name = "player")
	private int player;

	@Column(name = "winner")
	private int winner;

	@Column(name = "started")
	private boolean started;

	@Column(name = "activated")
	private boolean activated;

	@Column(name = "true_creator")
	private String trueCreator;

	@Column(name = "false_creator")
	private String falseCreator;

	@Column(name = "true_player")
	private String truePlayer;

	@Column(name = "false_player")
	private String falsePlayer;

	@OneToOne()
	@JoinColumn(name = "word_idfk", nullable = false)
	@JsonIgnore
	private Word word;
}
