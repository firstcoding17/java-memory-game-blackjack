package simpleblackjack;

import java.util.Scanner;

public class Blackjack {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		boolean uCardSetFull = false;
		boolean dCardSetFull = false;
		boolean uGameWin = true;
		boolean noSame[] = new boolean[52];		// 무작위 숫자 중복검사 대조
 
		// 무작위 숫자 결정 시 중복 불가를 위한 준비
		for(int i = 0; i < noSame.length ; i++) {
			noSame[i] = false;
		}

		// 총 결정될 개수만큼 중복없는 숫자가 나올 때까지 무작위 결정을 반복
		int comCardSet[] = new int[52];		// 무작위로 결정된 숫자를 저장
		int randLoop = 0;
		int randTemp;		// 무작위 숫자를 중복검사 전 임시로 저장
		while(randLoop < 52) {
			randTemp = (int)(Math.random() * 52);
			if(noSame[randTemp] == false) {
				noSame[randTemp] = true;
				comCardSet[randLoop] = randTemp + 1;
				randLoop++;
			}
		}

		System.out.print("카드 묶음");
		cardSetInfoPrint(comCardSet);

/*		///// 테스트: 공용 카드set에서 정상적으로 카드를 뽑는지 확인
 		// 카드 묶음에서 1장 뽑기
		int drawCard = cardSet[(int)(Math.random() * 52)];

		// 카드의 숫자 체크
		String cardNum = checkNum(drawCard);

		// 뽑은 카드 확인
		System.out.println("뽑은 카드는 " + cardNum + "입니다.");
*/

		int drawCard = 0;
		int cardSetPos = 0;

		///// 카드 2장이 A와 K면 승리(블랙잭)  ※ 게임을 강제로 종료
		///// 플레이어, 딜러 블랙잭 판단을 위한 저장 공간
		boolean uBlackjack[] = new boolean[2];
		boolean dBlackjack[] = new boolean[2];
		boolean uBlackjackWin = false;
		boolean dBlackjackWin = false;
		
		///// 카드 기본 셋팅: 자신 2장, 딜러 2장 배분
		int uCardSet[] = new int[6];
		int dCardSet[] = new int[3];
		basicCardSetInit(comCardSet, uCardSet, uBlackjack);
		basicCardSetInit(comCardSet, dCardSet, dBlackjack);
		
/*		// 테스트: 블랙잭 배열에 값이 제대로 들어갔는지 확인
		System.out.println("uBlackjack[0],[1] = " + uBlackjack[0] + " " + uBlackjack[1]);
		System.out.println("dBlackjack[0],[1] = " + dBlackjack[0] + " " + dBlackjack[1]);
*/		
		if (uBlackjack[0] == true && uBlackjack[1] == true) {
			uBlackjackWin = true;
		} 
		if (dBlackjack[0] == true && dBlackjack[1] == true) {
			dBlackjackWin = true;
		}
		
		// 테스트: 유저, 딜러의 최초 카드 'A(1), K(13)' 판단 결과 확인
//		System.out.println("uBlackjackWin = " + uBlackjackWin + ", dBlackjackWin = " + dBlackjackWin);
		
		System.out.println();
		
		
		// 자신에게 배분된 카드 숫자 출력
		System.out.println("자신에게 배분된 카드 숫자");
		cardSetNum(uCardSet);
		
		// 딜러에게 배분된 카드 숫자 출력
		System.out.println("딜러에게 배분된 카드 숫자");
		cardSetNum(dCardSet);
		
		System.out.println();
		
/*		// 테스트: 변경된 카드 묶음
		System.out.println();
		System.out.print("변경된 카드 묶음");
		cardSetInfoPrint(comCardSet);
*/		
		
		///// 게임 반복 /////
		int drawLoop2 = 0;
		while (true) {
			if (uBlackjackWin || dBlackjackWin) {
				break;
			} else if (!uGameWin) {
				break;
			}
			else {
			
			///// 플레이어에게 'Hit or Stay' 물어봄
			System.out.print("행동을 결정해주세요. Hit(1), Stay(2): ");
			int decision = scan.nextInt();
			System.out.println();
		
			
				///// 플레이어 'Hit' 결정 시 추가 카드 받음			
				// Hit(1) 시 카드 1장 뽑기
				if (decision == 1) {
		
					// 카드 뽑기 조건: 대상의 카드set이 비어있는 상태이어야 함
					if (!cardFull(uCardSet, uCardSetFull)) {
						// 카드 뽑기
						while (drawLoop2 < 1) {
							cardSetPos = (int)(Math.random() * 52);
							drawCard = comCardSet[cardSetPos];
		
							if (drawCard != 0) {
								// System.out.println("자신이 뽑은 카드의 배열 인덱스 = " + cardSetPos);
				
								drawCard = checkNum(drawCard);
								for (int i = 0; i < uCardSet.length; i++) {
									if (uCardSet[i] == 0) {
										uCardSet[i] = drawCard;
										comCardSet[cardSetPos] = 0;
										drawLoop2++;
										break;
									}
								}
							}
						}
						drawLoop2 = 0;
					}
					else {
						System.out.println("더 이상 카드를 받을 수 없습니다. 강제로 'Stay'됩니다.");
						decision = 2;
					}
					
					if (pointResult(uCardSet) > 21) {
						uGameWin = false;
						break;
					}
					
					System.out.println("자신에게 배분된 카드 숫자");
					cardSetNum(uCardSet);
					
					System.out.println("딜러에게 배분된 카드 숫자");
					cardSetNum(dCardSet);
					
				} else if (decision == 2) {
					///// 각 총합 비교 후 딜러의 총합이 플레이어보다 낮으면 딜러 추가 카드 1장  ※ 1회 한정
					
					// 카드 뽑기 조건: 딜러의 점수가 자신의 점수보다 낮고, 공간의 여유가 있는 상태여야 함(= 카드가 2개인 상태)
					if (pointResult(dCardSet) < pointResult(uCardSet) && !cardFull(dCardSet, dCardSetFull)) {		
						// 카드 뽑기
						while (drawLoop2 < 1) {
							cardSetPos = (int)(Math.random() * 52);
							drawCard = comCardSet[cardSetPos];
							if (drawCard != 0) {
								// System.out.println("딜러가 뽑은 카드의 배열 인덱스 = " + cardSetPos);
				
								drawCard = checkNum(drawCard);
								for (int i = 0; i < dCardSet.length; i++) {
									if (dCardSet[i] == 0) {
										dCardSet[i] = drawCard;
										comCardSet[cardSetPos] = 0;
										drawLoop2++;
										break;
									}
								}
							}
						}
						drawLoop2 = 0;
					}
					break;
				}
				else {
					System.out.println("잘못 입력하셨습니다. 다시 입력해주세요");
					System.out.println();
				}
			}
		}
	///// 게임 반복 종료 /////

/*
 * [결과 정리 표]
 * 
 * 1.플레이어 승리
 *  - 자신의 최초 카드 2장이 블랙잭(A, K)이고, 딜러는 아닐 경우
 *  - 'Stay' 결정 시 각자 총합이 21미만이고, 자신의 총합이 높을 경우
 *  - 'Stay' 결정 시 자신의 총합은 21이만이고, 딜러는 21을 초과할 경우
 *  
 * 2.플레이어 패배
 *  - 딜러의 최초 카드 2장이 블랙잭(A, K)이고, 자신은 아닐 경우
 *  - 'Stay' 결정 시 자신의 총합이 21을 초과할 경우
 *  - 'Stay' 결정 시 각자 총합이 21미만이고, 딜러의 총합이 높을 경우
 *  
 * 3.무승부
 *  - 'Stay' 결정 시 각자 총합이 동일한 경우
 *  - 각자 최초의 카드 2장이 블랙잭(A, K)일 경우
 */
		
		///// 결과 출력
		System.out.println();
		System.out.println("카드를 오픈합니다.");
		System.out.println();
		
		// 테스트: 자신에게 배분된 카드 숫자 출력
		System.out.println("자신에게 배분된 카드 숫자");
		cardSetNum(uCardSet);
		
		System.out.println("자신의 점수 = " + pointResult(uCardSet));
		System.out.println();
		
		// 테스트: 딜러에게 배분된 카드 숫자 출력
		System.out.println("딜러에게 배분된 카드 숫자");
		cardSetNum(dCardSet);
		
		System.out.println("딜러의 점수 = " + pointResult(dCardSet));
		System.out.println();
		
		if (uBlackjackWin && !dBlackjackWin) {
			System.out.println("대박! ★ 축하합니다 ★ '블랙잭'으로 승리!!");
		} else if (!uBlackjackWin && dBlackjackWin) {
			System.out.println("헐... '블랙잭'으로 패배!");
		} else if (uBlackjackWin && dBlackjackWin) {
			System.out.println("이럴수가!! '블랙잭'으로 무승부!!!");
		}
		else {
			if (uGameWin && pointResult(uCardSet) > pointResult(dCardSet) 
					&& pointResult(uCardSet) <= 21
					|| uGameWin && pointResult(uCardSet) < pointResult(dCardSet)
					&& pointResult(dCardSet) > 21) {
				System.out.println("결과 = ★ 축하합니다 ★ 승리하셨습니다!!");
			} else if (uGameWin && pointResult(uCardSet) == pointResult(dCardSet)
					&& pointResult(uCardSet) <= 21) {
				System.out.println("이럴수가... 무승부입니다!");
			}
			else {
				System.out.println("결과 = 패배!");
			}
		}

		///// 딜러의 숨겨진 카드 공개
		

		scan.close();
	}

////////////////////////// 메소드 영역

	public static void basicCardSetInit(int comCardSet[], int cardSet[], boolean blackjack[]) {
		int drawCard = 0;
		int cardSetPos = 0;
		int drawLoop = 0;
		
		// 자신의 최초 카드배분 루프  ※ 1회용
		// 카드 묶음에서 뽑아진 카드는 더 이상 뽑히지 않음
		while (drawLoop < 2) {
			cardSetPos = (int)(Math.random() * 52);
			drawCard = comCardSet[cardSetPos];
			if (drawCard != 0) {
				// System.out.println("자신이 뽑은 카드의 배열 인덱스 = " + cardSetPos);
				
				drawCard = checkNum(drawCard, blackjack);
				cardSet[drawLoop] = drawCard;
				comCardSet[cardSetPos] = 0;
				drawLoop++;
			}
		}

/*		// 테스트: 블랙잭
		cardSet[0] = checkNum(1, blackjack);
		cardSet[1] = checkNum(13, blackjack);
*/
	}
		
	// 카드번호 체크 및 변환 + 블랙잭 판단
	public static int checkNum(int cardNum, boolean blackjack[]) {
		int result = 0;
 
		// 실제 카드 숫자로 변환
		if (cardNum == 1) {
			blackjack[0] = true;
		} else if (cardNum % 13 == 0) {
			cardNum = 13;
			blackjack[1] = true;
		}
		else {
			cardNum = (cardNum % 13);
		}

		// 카드 번호가 '11' 이상이면 '10'으로 변경
		if (cardNum > 10) {
			switch (cardNum) {
				case 11:
					result = 10;
					break;
				case 12:
					result = 10;
					break;
				case 13:
					result = 10;
					break;
			}
		}
		else {
			result = cardNum;
		}

		return result;
    }
	
	// 카드번호 체크 및 변환
	public static int checkNum(int cardNum) {
		int result = 0;
 
		// 실제 카드 숫자로 변환
		if (cardNum == 0) {
			cardNum = (cardNum % 13);
		} else if (cardNum % 13 == 0) {
			cardNum = 13;
		}
		else {
			cardNum = (cardNum % 13);
		}

		// 카드 번호가 '11' 이상이면 '10'으로 변경
		if (cardNum > 10) {
			switch (cardNum) {
				case 11:
					result = 10;
					break;
				case 12:
					result = 10;
					break;
				case 13:
					result = 10;
					
					break;
			}
		}
		else {
			result = cardNum;
		}

		return result;
    }
	
	public static void cardSetInfoPrint(int cardSet[]) {
		for(int i=0; i < 52; i++) {
			if (i % 3 == 0) {
				System.out.println();
			}
			System.out.print("cardSet[" + i + "] = " + cardSet[i] + " ");
		}
		System.out.println("\n");
	}
	
	// 배분된 카드 숫자 출력
	public static void cardSetNum(int cardSet[]) {
		for (int i = 0; i < cardSet.length; i++) {
			System.out.print(cardSet[i] + " ");
		}
		System.out.println();
	}
	
	// 자신의 카드set이 꽉 찬 상태인지 체크
	public static boolean cardFull(int cardSet[], boolean cardSetFull) {
		int count = 0;
		for (int i = 0; i < cardSet.length; i++) {
			if (cardSet[i] == 0) {
				count++;
			}
		}
		// 비어있는 개수가 없으면 꽉 찬 상태
		if (count == 0) {
			cardSetFull = true;
		}
		return cardSetFull;
	}
	
	// 점수 구하기
	public static int pointResult(int cardSet[]) {
		int point = 0;
		for (int i = 0; i < cardSet.length; i++) {
			point += cardSet[i];
		}
		
		// 자신의 카드set에'A'(1)가 있는지 확인
		for (int i = 0; i < cardSet.length; i++) {
			if (cardSet[i] == 1 && point + 10 <= 21) {
				point += 10;
			}
		}
		return point;
	}
}

