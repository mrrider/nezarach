package com.sheremet.checkers.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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

        // deep 3
        prepareFirstSteps(board, steps, hits);
//        prepareNextSteps(board, steps, hits, opposite(color), 1);
//        prepareNextSteps(board, steps, hits, color, 2);
//
//        // deep 5
//        prepareNextSteps(board, steps, hits, opposite(color), 3);
//        prepareNextSteps(board, steps, hits, color, 4);

        Step s = hits.isEmpty() ? steps.get(0) : hits.get(0);
        return s;
    }

    private CheckerColor opposite(CheckerColor checkerColor) {
        if (checkerColor == CheckerColor.BLACK)
            return CheckerColor.WHITE;

        return CheckerColor.BLACK;
    }

    private void prepareFirstSteps(final Board board, List<Step> steps, List<Step> hits) {
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

//    private void prepareNextSteps(final Board original, List<StepSolution> steps, List<StepSolution> hits, CheckerColor checkerColor, int deep) {
//        if (!hits.isEmpty()) {
//            Set<StepSolution> toRemove = new HashSet<>();
//            List<StepSolution> toAdd = new ArrayList<>();
//            for (StepSolution solution : hits) {
//                if (solution.size() < deep)
//                    continue;
//
//                try {
//                    Board clone = original.clone();
//                    for (Step step : solution.getSteps()) {
//                        clone.apply(step);
//                    }
//
//                    Step last = solution.getSteps().get(solution.getSteps().size() - 1);
//                    Checker checker = clone.get(last.getSteps().get(last.getSteps().size() - 1).getTo());
//                    Position cp = checker.getPosition(), next, nextToNext;
//
//                    // up & right
//                    next = new Position(cp.getX() + 1, cp.getY() + 1);
//                    if (ValidationUtil.validPosition(next)) {
//                        Checker checkerAtNext = clone.get(next);
//                        nextToNext = new Position(next.getX() + 1, next.getY() + 1);
//                        if (checkerAtNext != null && checkerAtNext.getColor() != checkerColor && ValidationUtil.validPosition(nextToNext) && clone.get(nextToNext) == null) {
//                            StepSolution stepSolution = solution.clone();
//                            stepSolution.addStep(new Step(Arrays.asList(new StepUnit(cp, nextToNext))));
//                            toAdd.add(stepSolution);
//                            toRemove.add(solution);
//                        }
//                    }
//
//                    // up & left
//                    next = new Position(cp.getX() - 1, cp.getY() + 1);
//                    if (ValidationUtil.validPosition(next)) {
//                        Checker checkerAtNext = clone.get(next);
//                        nextToNext = new Position(next.getX() - 1, next.getY() + 1);
//                        if (checkerAtNext != null && checkerAtNext.getColor() != checkerColor && ValidationUtil.validPosition(nextToNext) && clone.get(nextToNext) == null) {
//                            StepSolution stepSolution = solution.clone();
//                            stepSolution.addStep(new Step(Arrays.asList(new StepUnit(cp, nextToNext))));
//                            toAdd.add(stepSolution);
//                            toRemove.add(solution);
//                        }
//                    }
//
//                    // down & left
//                    next = new Position(cp.getX() - 1, cp.getY() - 1);
//                    if (ValidationUtil.validPosition(next)) {
//                        Checker checkerAtNext = clone.get(next);
//                        nextToNext = new Position(next.getX() - 1, next.getY() - 1);
//                        if (checkerAtNext != null && checkerAtNext.getColor() != checkerColor && ValidationUtil.validPosition(nextToNext) && clone.get(nextToNext) == null) {
//                            StepSolution stepSolution = solution.clone();
//                            stepSolution.addStep(new Step(Arrays.asList(new StepUnit(cp, nextToNext))));
//                            toAdd.add(stepSolution);
//                            toRemove.add(solution);
//                        }
//                    }
//
//                    // down & right
//                    next = new Position(cp.getX() + 1, cp.getY() - 1);
//                    if (ValidationUtil.validPosition(next)) {
//                        Checker checkerAtNext = clone.get(next);
//                        nextToNext = new Position(next.getX() + 1, next.getY() - 1);
//                        if (checkerAtNext != null && checkerAtNext.getColor() != checkerColor && ValidationUtil.validPosition(nextToNext) && clone.get(nextToNext) == null) {
//                            StepSolution stepSolution = solution.clone();
//                            stepSolution.addStep(new Step(Arrays.asList(new StepUnit(cp, nextToNext))));
//                            toAdd.add(stepSolution);
//                            toRemove.add(solution);
//                        }
//                    }
//                } catch (Exception e) {
//
//                }
//            }
//
//            hits.removeAll(toRemove);
//            hits.addAll(toAdd);
//        } else if(!steps.isEmpty()) {
//            Set<StepSolution> toRemove = new HashSet<>();
//            List<StepSolution> toAdd = new ArrayList<>();
//            for (StepSolution solution : steps) {
//                if (solution.size() < deep)
//                    continue;
//
//                try {
//                    Board clone = original.clone();
//                    for (Step step : solution.getSteps()) {
//                        clone.apply(step);
//                    }
//
//                    Step last = solution.getSteps().get(solution.getSteps().size() - 1);
//                    Checker checker = clone.get(last.getSteps().get(last.getSteps().size() - 1).getTo());
//                    Position cp = checker.getPosition(), next;
//
//                    // up & right
//                    next = new Position(cp.getX() + 1, cp.getY() + 1);
//                    if (ValidationUtil.validPosition(next)) {
//                        try {
//                            Step step = new Step(Arrays.asList(new StepUnit(cp, next)));
//                            clone.clone().apply(step);
//                            StepSolution stepSolution = solution.clone();
//                            stepSolution.addStep(step);
//                            toAdd.add(stepSolution);
//                            toRemove.add(solution);
//                        } catch (Exception e) {
//
//                        }
//                    }
//
//                    // up & left
//                    next = new Position(cp.getX() - 1, cp.getY() + 1);
//                    if (ValidationUtil.validPosition(next)) {
//                        try {
//                            Step step = new Step(Arrays.asList(new StepUnit(cp, next)));
//                            clone.clone().apply(step);
//                            StepSolution stepSolution = solution.clone();
//                            stepSolution.addStep(step);
//                            toAdd.add(stepSolution);
//                            toRemove.add(solution);
//                        } catch (Exception e) {
//
//                        }
//                    }
//
//                    // down & left
//                    next = new Position(cp.getX() - 1, cp.getY() - 1);
//                    if (ValidationUtil.validPosition(next)) {
//                        try {
//                            Step step = new Step(Arrays.asList(new StepUnit(cp, next)));
//                            clone.clone().apply(step);
//                            StepSolution stepSolution = solution.clone();
//                            stepSolution.addStep(step);
//                            toAdd.add(stepSolution);
//                            toRemove.add(solution);
//                        } catch (Exception e) {
//
//                        }
//                    }
//
//                    // down & right
//                    next = new Position(cp.getX() + 1, cp.getY() - 1);
//                    if (ValidationUtil.validPosition(next)) {
//                        try {
//                            Step step = new Step(Arrays.asList(new StepUnit(cp, next)));
//                            clone.clone().apply(step);
//                            StepSolution stepSolution = solution.clone();
//                            stepSolution.addStep(step);
//                            toAdd.add(stepSolution);
//                            toRemove.add(solution);
//                        } catch (Exception e) {
//
//                        }
//                    }
//                } catch (Exception e) {
//
//                }
//            }
//
//            steps.removeAll(toRemove);
//            steps.addAll(toAdd);
//        }
//    }

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
