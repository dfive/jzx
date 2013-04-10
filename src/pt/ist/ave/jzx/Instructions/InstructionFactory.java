package pt.ist.ave.jzx.Instructions;

public class InstructionFactory {

	public static Instruction getInstruction(short opCode) {
		switch(opCode) {
		case 0x20:
			return new HALT(opCode);
		case 0x0b:
			return new DEC_BC(opCode);
		case 0x78:
			return new LDAB(opCode);
		case 0xb1:
			return new OR_C(opCode);
		case 0xd9:
			return new EXX(opCode);
		case 0xc1:
			return new POP_BC(opCode);
		case 0x12:
			return new LDDEA(opCode);
		case 0xd5:
			return new PUSHDE(opCode);
		case 0xc5:
			return new PUSH_BC(opCode);
		case 0xd1:
			return new POPDE(opCode);
		case 0x08:
			return new EXAFAF(opCode);
		case 0xf1:
			return new POPAF(opCode);
		case 0xf5:
			return new PUSH_AF(opCode);
		case 0x7e:
			return new LDAHL(opCode);
		case 0xe1:
			return new POP_HL(opCode);
		case 0x10:
			return new DJNZD(opCode);
		case 0x24:
			return new INC_H(opCode);
		case 0x3a:
			return new LDANN(opCode);
		case 0xa7:
			return new AND_A(opCode);
		case 0x3d:
			return new DEC_A(opCode);
		}
		return null;
	}
}
