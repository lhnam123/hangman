package com.hangman.game.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private Integer creator;

    @Column(name = "player")
    private Integer player;

    @Column(name = "winner")
    private Integer winner;

    @Builder.Default
    @Column(name = "step_fail_creator")
    private int stepFailCreator = 0;

    @Builder.Default
    @Column(name = "step_fail_player")
    private int stepFailPlayer = 0;

    @Builder.Default
    @Column(name = "step_true_creator")
    private Integer stepTrueCreator = 0;

    @Builder.Default
    @Column(name = "step_true_player")
    private Integer stepTruePlayer = 0;

    @Builder.Default
    @Column(name = "status")
    private String status = "waiting";

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

    public Integer getEnemyId(final int userId) {
        if (userId == creator) {
            return player;
        }
        return creator;
    }
}
