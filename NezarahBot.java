package com.sheremet.checkers.client;

import java.awt.Point;
import java.util.List;
import java.util.Random;

import checkers.client.CheckersBot;
import checkers.pojo.board.Board;
import checkers.pojo.board.Letters;
import checkers.pojo.board.Numbers;
import checkers.pojo.board.StepCollector;
import checkers.pojo.checker.Checker;
import checkers.pojo.checker.CheckerColor;
import checkers.pojo.checker.Position;
import checkers.pojo.exceptions.DoOneMoreStepException;
import checkers.pojo.step.Step;
import checkers.pojo.step.StepUnit;
import checkers.utils.Validator;

public class NezarahBot implements CheckersBot{
	private CheckerColor color;
	private String name;
	private BoardRenderer renderer;
	private static final long timeLimit = 4000;
	public NezarahBot(String name, BoardRenderer renderer) {
		this.name=name;
		this.renderer = renderer;
	}
	public void onGameStart(CheckerColor checkerColor) {
		color = checkerColor;
		System.out.println(checkerColor);
	}

	public Step next(Board board) {
		long timeIn = System.currentTimeMillis();
		List<Checker> myCheckers = board.get(color);
		StepCollector collector = new StepCollector();
		Step finalStep = null;
		Step randomStep = null;
		
		for(Checker checker:myCheckers){
			
			if(System.currentTimeMillis() < timeIn + timeLimit){
				if(collector.getKillSteps(board, checker).size()>0){
					System.out.println("fdfdsfsdfdfsdfsdfsdfs");
					for(int i = 0; i < collector.getKillSteps(board, checker).size(); i++){
						finalStep.addStep(collector.getKillSteps(board, checker).get(i));
						System.out.println(i);
					}
					if (new Validator().isValidStep(board, finalStep, color)){
						System.out.println("fdfdsfdfs");
						try{
							board.apply(finalStep);
							return finalStep;
						}catch(IllegalArgumentException e){
							System.out.println("tutuuuu");
							if (e.getCause() instanceof DoOneMoreStepException){
								
							}
						}
					}
//					
//					board.apply(finalStep);
				}else {
					if(collector.getCommonSteps(board, checker).size() > 0){
						finalStep = collector.getCommonSteps(board, checker).get(0);
						
						System.out.println("common " + collector.getCommonSteps(board, checker));
//						
					}else{
						
					}
				}
//				
//				System.out.println("steps " + collector.getSteps(board).get(0).getSteps().get(0).getFrom() + " - " + collector.getSteps(board).get(0).getSteps().get(0).getTo());
//				System.out.println("common  " + collector.getCommonSteps(board, checker));
//				System.out.println("kill  " + collector.getKillSteps(board, checker));
//				System.out.println("deep  " + collector.getDeepKillSteps(board, null, color, null));
			}else{
				int i = (int) Math.random()*(collector.getSteps(board).size() - 0);
				randomStep =  collector.getSteps(board).get(i);
			}
			
//			for(int x=1; x<=8; x++){
//				for(int y=1; y<=8; y++){
//					if ((x+y)%2==0){
//						try{
							
							
//							Position p = new Position(x, y);
//							StepUnit stepUnit = new StepUnit(checker.getPosition(), p);
//							Step step = finalStep!=null?finalStep:new Step();
//							step.addStep(stepUnit);
//							if (new Validator().isValidStep(board, step, color)){
//								try{
//									board.apply(step);
//								}catch(IllegalArgumentException e){
//									if (e.getCause() instanceof DoOneMoreStepException){
//										inner:while(true){
//											for(int x1=1; x1<=8; x1++){
//												for(int y1=1; y1<=8; y1++){
//													if ((x+y)%2==0){
//														try{
//															Position p1 = new Position(x1, y1);
//															StepUnit stepUnit1 = new StepUnit(step.getSteps().get(step.getSteps().size()-1).getTo(), p1);
//															step.addStep(stepUnit1);
//															if (new Validator().isValidStep(board, step, color)){
//																try{
//																	board.apply(step);
//																	break inner;
//																}catch(IllegalArgumentException e2){
//																	if (e2.getCause() instanceof DoOneMoreStepException){
//																		continue inner;
//																	}else{
//																		throw e2;
//																	}
//																}
//															}else{
//																step.getSteps().remove(step.getSteps().size()-1);
//															}
//														}catch(IllegalArgumentException e2){
//															step.getSteps().remove(step.getSteps().size()-1);
//														}
//													}
//												}
//											}
//											break outer;
//										}
//									}else{
//										throw e;
//									}
//								}
//								finalStep = step;
//								break outer;
//							}
//						}catch(IllegalArgumentException e){ }
//					}
//				}
//			}
		}
		System.out.println("final step - " + finalStep);
		if(finalStep == null && randomStep != null){
			return randomStep;
		}
//		System.out.println(finalStep);
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		return finalStep;
	}
	//this function return random 
    private int randGet(int from, int to){
    	return (int) Math.random()*(to - from);
	}
	/**
	 * Ends the game with message
	 * @param msg - message on the game ends
	 */
	public void onGameEnd(String msg) {
		renderer.showMsg(msg);
		System.out.println(msg);
	}

	public String clientBotName() {
		return name;
	}
	
	@Override
	public void show(Board board) {
		renderer.render(board);
	}
}
