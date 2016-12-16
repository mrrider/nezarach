package com.sheremet.checkers.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import checkers.client.CheckersBot;
import checkers.pojo.board.Board;
import checkers.pojo.checker.Checker;
import checkers.pojo.checker.CheckerColor;
import checkers.pojo.checker.Position;
import checkers.pojo.step.Step;
import checkers.pojo.step.StepUnit;
import checkers.utils.Validator;

public class NezarahBot implements CheckersBot{

    private CheckerColor color;
    private final String name;
    private BoardRenderer renderer;
    private Validator validator;
    

    public NezarahBot(BoardRenderer renderer) {
        this.name = "NezarachBot";
        this.renderer = renderer;
        this.validator = new Validator();
    }

    @Override
    public Step next(final Board board) {
        List<Step> hits = new ArrayList<>();
        List<Step> steps = new ArrayList<>();

        prepareStep(board, steps, hits);
        Step s;
        if(!hits.isEmpty()){
            int i = hits.size();
            s = hits.get(new Random().nextInt(i));
        }else{
            int i = steps.size();
            s = steps.get(new Random().nextInt(i));
        }
        return s;
    }

    private void prepareStep(final Board board, List<Step> steps, List<Step> hits) {
        for (Checker checker : board.get(color)) {
            Position next;
            Position nextToNext;
            Position ch = checker.getPosition();

            // up & right
            next = new Position(ch.getX() + 1, ch.getY() + 1);
            if (next.getLetter() != null && next.getNumber() != null) {
                nextToNext = new Position(next.getX() + 1, next.getY() + 1);
                addStepForChecked(board, checker, next, nextToNext, steps, hits, color);
            }


            // up & left
            next = new Position(ch.getX() - 1, ch.getY() + 1);
            if (next.getLetter() != null && next.getNumber() != null) {
                nextToNext = new Position(next.getX() - 1, next.getY() + 1);
                addStepForChecked(board, checker, next, nextToNext, steps, hits, color);
            }

            // down & left
            next = new Position(ch.getX() - 1, ch.getY() - 1);
            if (next.getLetter() != null && next.getNumber() != null) {
                nextToNext = new Position(next.getX() - 1, next.getY() - 1);
                addStepForChecked(board, checker, next, nextToNext, steps, hits, color);
            }

            // down & right
            next = new Position(ch.getX() + 1, ch.getY() - 1);
            if (next.getLetter() != null && next.getNumber() != null) {
                nextToNext = new Position(next.getX() + 1, next.getY() - 1);
                addStepForChecked(board, checker, next, nextToNext, steps, hits, color);
            }
        }
    }

    private void addStepForChecked(final Board board, Checker checker, Position next, Position nextToNext, List<Step> steps, List<Step> hits, CheckerColor checkerColor) {
        Checker c = board.get(next);

        if (c != null) {
            if (c.getColor() != checkerColor) {
                // if position next to opposite player check is valid and empty
                if (nextToNext.getLetter() != null && nextToNext.getNumber() != null && board.get(nextToNext) == null) {
                    // should not check if step is valid cause we can always hit another checker
                    hits.add(new Step(Arrays.asList(new StepUnit(checker.getPosition(), nextToNext))));
                }
            }
        } else {
            Step s = new Step(Arrays.asList(new StepUnit(checker.getPosition(), next)));
            try {
                // check if valid step for board
                board.apply(s);
                steps.add(s);
            } catch (Exception e) {

            }
        }
    }

    @Override
    public void onGameStart(CheckerColor checkerColor) {
        color = checkerColor;
    }

    @Override
    public void onGameEnd(String msg) {
        renderer.showMsg(msg);
    }

    @Override
    public String clientBotName() {
        return name;
    }

    @Override
    public void show(Board board) {
        renderer.render(board);
    }
}
