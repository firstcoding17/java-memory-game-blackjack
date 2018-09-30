package simpleblackjack;

import java.util.Scanner;

public class Blackjack {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		boolean uCardSetFull = false;
		boolean dCardSetFull = false;
		boolean uGameWin = true;
		boolean noSame[] = new boolean[52];		// ������ ���� �ߺ��˻� ����
 
		// ������ ���� ���� �� �ߺ� �Ұ��� ���� �غ�
		for(int i = 0; i < noSame.length ; i++) {
			noSame[i] = false;
		}

		// �� ������ ������ŭ �ߺ����� ���ڰ� ���� ������ ������ ������ �ݺ�
		int comCardSet[] = new int[52];		// �������� ������ ���ڸ� ����
		int randLoop = 0;
		int randTemp;		// ������ ���ڸ� �ߺ��˻� �� �ӽ÷� ����
		while(randLoop < 52) {
			randTemp = (int)(Math.random() * 52);
			if(noSame[randTemp] == false) {
				noSame[randTemp] = true;
				comCardSet[randLoop] = randTemp + 1;
				randLoop++;
			}
		}

		System.out.print("ī�� ����");
		cardSetInfoPrint(comCardSet);

/*		///// �׽�Ʈ: ���� ī��set���� ���������� ī�带 �̴��� Ȯ��
 		// ī�� �������� 1�� �̱�
		int drawCard = cardSet[(int)(Math.random() * 52)];

		// ī���� ���� üũ
		String cardNum = checkNum(drawCard);

		// ���� ī�� Ȯ��
		System.out.println("���� ī��� " + cardNum + "�Դϴ�.");
*/

		int drawCard = 0;
		int cardSetPos = 0;

		///// ī�� 2���� A�� K�� �¸�(����)  �� ������ ������ ����
		///// �÷��̾�, ���� ���� �Ǵ��� ���� ���� ����
		boolean uBlackjack[] = new boolean[2];
		boolean dBlackjack[] = new boolean[2];
		boolean uBlackjackWin = false;
		boolean dBlackjackWin = false;
		
		///// ī�� �⺻ ����: �ڽ� 2��, ���� 2�� ���
		int uCardSet[] = new int[6];
		int dCardSet[] = new int[3];
		basicCardSetInit(comCardSet, uCardSet, uBlackjack);
		basicCardSetInit(comCardSet, dCardSet, dBlackjack);
		
/*		// �׽�Ʈ: ���� �迭�� ���� ����� ������ Ȯ��
		System.out.println("uBlackjack[0],[1] = " + uBlackjack[0] + " " + uBlackjack[1]);
		System.out.println("dBlackjack[0],[1] = " + dBlackjack[0] + " " + dBlackjack[1]);
*/		
		if (uBlackjack[0] == true && uBlackjack[1] == true) {
			uBlackjackWin = true;
		} 
		if (dBlackjack[0] == true && dBlackjack[1] == true) {
			dBlackjackWin = true;
		}
		
		// �׽�Ʈ: ����, ������ ���� ī�� 'A(1), K(13)' �Ǵ� ��� Ȯ��
//		System.out.println("uBlackjackWin = " + uBlackjackWin + ", dBlackjackWin = " + dBlackjackWin);
		
		System.out.println();
		
		
		// �ڽſ��� ��е� ī�� ���� ���
		System.out.println("�ڽſ��� ��е� ī�� ����");
		cardSetNum(uCardSet);
		
		// �������� ��е� ī�� ���� ���
		System.out.println("�������� ��е� ī�� ����");
		cardSetNum(dCardSet);
		
		System.out.println();
		
/*		// �׽�Ʈ: ����� ī�� ����
		System.out.println();
		System.out.print("����� ī�� ����");
		cardSetInfoPrint(comCardSet);
*/		
		
		///// ���� �ݺ� /////
		int drawLoop2 = 0;
		while (true) {
			if (uBlackjackWin || dBlackjackWin) {
				break;
			} else if (!uGameWin) {
				break;
			}
			else {
			
			///// �÷��̾�� 'Hit or Stay' ���
			System.out.print("�ൿ�� �������ּ���. Hit(1), Stay(2): ");
			int decision = scan.nextInt();
			System.out.println();
		
			
				///// �÷��̾� 'Hit' ���� �� �߰� ī�� ����			
				// Hit(1) �� ī�� 1�� �̱�
				if (decision == 1) {
		
					// ī�� �̱� ����: ����� ī��set�� ����ִ� �����̾�� ��
					if (!cardFull(uCardSet, uCardSetFull)) {
						// ī�� �̱�
						while (drawLoop2 < 1) {
							cardSetPos = (int)(Math.random() * 52);
							drawCard = comCardSet[cardSetPos];
		
							if (drawCard != 0) {
								// System.out.println("�ڽ��� ���� ī���� �迭 �ε��� = " + cardSetPos);
				
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
						System.out.println("�� �̻� ī�带 ���� �� �����ϴ�. ������ 'Stay'�˴ϴ�.");
						decision = 2;
					}
					
					if (pointResult(uCardSet) > 21) {
						uGameWin = false;
						break;
					}
					
					System.out.println("�ڽſ��� ��е� ī�� ����");
					cardSetNum(uCardSet);
					
					System.out.println("�������� ��е� ī�� ����");
					cardSetNum(dCardSet);
					
				} else if (decision == 2) {
					///// �� ���� �� �� ������ ������ �÷��̾�� ������ ���� �߰� ī�� 1��  �� 1ȸ ����
					
					// ī�� �̱� ����: ������ ������ �ڽ��� �������� ����, ������ ������ �ִ� ���¿��� ��(= ī�尡 2���� ����)
					if (pointResult(dCardSet) < pointResult(uCardSet) && !cardFull(dCardSet, dCardSetFull)) {		
						// ī�� �̱�
						while (drawLoop2 < 1) {
							cardSetPos = (int)(Math.random() * 52);
							drawCard = comCardSet[cardSetPos];
							if (drawCard != 0) {
								// System.out.println("������ ���� ī���� �迭 �ε��� = " + cardSetPos);
				
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
					System.out.println("�߸� �Է��ϼ̽��ϴ�. �ٽ� �Է����ּ���");
					System.out.println();
				}
			}
		}
	///// ���� �ݺ� ���� /////

/*
 * [��� ���� ǥ]
 * 
 * 1.�÷��̾� �¸�
 *  - �ڽ��� ���� ī�� 2���� ����(A, K)�̰�, ������ �ƴ� ���
 *  - 'Stay' ���� �� ���� ������ 21�̸��̰�, �ڽ��� ������ ���� ���
 *  - 'Stay' ���� �� �ڽ��� ������ 21�̸��̰�, ������ 21�� �ʰ��� ���
 *  
 * 2.�÷��̾� �й�
 *  - ������ ���� ī�� 2���� ����(A, K)�̰�, �ڽ��� �ƴ� ���
 *  - 'Stay' ���� �� �ڽ��� ������ 21�� �ʰ��� ���
 *  - 'Stay' ���� �� ���� ������ 21�̸��̰�, ������ ������ ���� ���
 *  
 * 3.���º�
 *  - 'Stay' ���� �� ���� ������ ������ ���
 *  - ���� ������ ī�� 2���� ����(A, K)�� ���
 */
		
		///// ��� ���
		System.out.println();
		System.out.println("ī�带 �����մϴ�.");
		System.out.println();
		
		// �׽�Ʈ: �ڽſ��� ��е� ī�� ���� ���
		System.out.println("�ڽſ��� ��е� ī�� ����");
		cardSetNum(uCardSet);
		
		System.out.println("�ڽ��� ���� = " + pointResult(uCardSet));
		System.out.println();
		
		// �׽�Ʈ: �������� ��е� ī�� ���� ���
		System.out.println("�������� ��е� ī�� ����");
		cardSetNum(dCardSet);
		
		System.out.println("������ ���� = " + pointResult(dCardSet));
		System.out.println();
		
		if (uBlackjackWin && !dBlackjackWin) {
			System.out.println("���! �� �����մϴ� �� '����'���� �¸�!!");
		} else if (!uBlackjackWin && dBlackjackWin) {
			System.out.println("��... '����'���� �й�!");
		} else if (uBlackjackWin && dBlackjackWin) {
			System.out.println("�̷�����!! '����'���� ���º�!!!");
		}
		else {
			if (uGameWin && pointResult(uCardSet) > pointResult(dCardSet) 
					&& pointResult(uCardSet) <= 21
					|| uGameWin && pointResult(uCardSet) < pointResult(dCardSet)
					&& pointResult(dCardSet) > 21) {
				System.out.println("��� = �� �����մϴ� �� �¸��ϼ̽��ϴ�!!");
			} else if (uGameWin && pointResult(uCardSet) == pointResult(dCardSet)
					&& pointResult(uCardSet) <= 21) {
				System.out.println("�̷�����... ���º��Դϴ�!");
			}
			else {
				System.out.println("��� = �й�!");
			}
		}

		///// ������ ������ ī�� ����
		

		scan.close();
	}

////////////////////////// �޼ҵ� ����

	public static void basicCardSetInit(int comCardSet[], int cardSet[], boolean blackjack[]) {
		int drawCard = 0;
		int cardSetPos = 0;
		int drawLoop = 0;
		
		// �ڽ��� ���� ī���� ����  �� 1ȸ��
		// ī�� �������� �̾��� ī��� �� �̻� ������ ����
		while (drawLoop < 2) {
			cardSetPos = (int)(Math.random() * 52);
			drawCard = comCardSet[cardSetPos];
			if (drawCard != 0) {
				// System.out.println("�ڽ��� ���� ī���� �迭 �ε��� = " + cardSetPos);
				
				drawCard = checkNum(drawCard, blackjack);
				cardSet[drawLoop] = drawCard;
				comCardSet[cardSetPos] = 0;
				drawLoop++;
			}
		}

/*		// �׽�Ʈ: ����
		cardSet[0] = checkNum(1, blackjack);
		cardSet[1] = checkNum(13, blackjack);
*/
	}
		
	// ī���ȣ üũ �� ��ȯ + ���� �Ǵ�
	public static int checkNum(int cardNum, boolean blackjack[]) {
		int result = 0;
 
		// ���� ī�� ���ڷ� ��ȯ
		if (cardNum == 1) {
			blackjack[0] = true;
		} else if (cardNum % 13 == 0) {
			cardNum = 13;
			blackjack[1] = true;
		}
		else {
			cardNum = (cardNum % 13);
		}

		// ī�� ��ȣ�� '11' �̻��̸� '10'���� ����
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
	
	// ī���ȣ üũ �� ��ȯ
	public static int checkNum(int cardNum) {
		int result = 0;
 
		// ���� ī�� ���ڷ� ��ȯ
		if (cardNum == 0) {
			cardNum = (cardNum % 13);
		} else if (cardNum % 13 == 0) {
			cardNum = 13;
		}
		else {
			cardNum = (cardNum % 13);
		}

		// ī�� ��ȣ�� '11' �̻��̸� '10'���� ����
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
	
	// ��е� ī�� ���� ���
	public static void cardSetNum(int cardSet[]) {
		for (int i = 0; i < cardSet.length; i++) {
			System.out.print(cardSet[i] + " ");
		}
		System.out.println();
	}
	
	// �ڽ��� ī��set�� �� �� �������� üũ
	public static boolean cardFull(int cardSet[], boolean cardSetFull) {
		int count = 0;
		for (int i = 0; i < cardSet.length; i++) {
			if (cardSet[i] == 0) {
				count++;
			}
		}
		// ����ִ� ������ ������ �� �� ����
		if (count == 0) {
			cardSetFull = true;
		}
		return cardSetFull;
	}
	
	// ���� ���ϱ�
	public static int pointResult(int cardSet[]) {
		int point = 0;
		for (int i = 0; i < cardSet.length; i++) {
			point += cardSet[i];
		}
		
		// �ڽ��� ī��set��'A'(1)�� �ִ��� Ȯ��
		for (int i = 0; i < cardSet.length; i++) {
			if (cardSet[i] == 1 && point + 10 <= 21) {
				point += 10;
			}
		}
		return point;
	}
}

