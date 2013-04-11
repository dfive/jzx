package pt.ist.ave.jzx.Instructions;

import pt.ist.ave.jzx.Instructions.ld.LD_AB;
import pt.ist.ave.jzx.Instructions.ld.LD_AC;
import pt.ist.ave.jzx.Instructions.ld.LD_AD;
import pt.ist.ave.jzx.Instructions.ld.LD_AH;
import pt.ist.ave.jzx.Instructions.ld.LD_AL;
import pt.ist.ave.jzx.Instructions.ld.LD_AMDE;
import pt.ist.ave.jzx.Instructions.ld.LD_AMHL;
import pt.ist.ave.jzx.Instructions.ld.LD_AMNN;
import pt.ist.ave.jzx.Instructions.ld.LD_AN;
import pt.ist.ave.jzx.Instructions.ld.LD_BA;
import pt.ist.ave.jzx.Instructions.ld.LD_BCNN;
import pt.ist.ave.jzx.Instructions.ld.LD_BMHL;
import pt.ist.ave.jzx.Instructions.ld.LD_BN;
import pt.ist.ave.jzx.Instructions.ld.LD_CA;
import pt.ist.ave.jzx.Instructions.ld.LD_CD;
import pt.ist.ave.jzx.Instructions.ld.LD_CMHL;
import pt.ist.ave.jzx.Instructions.ld.LD_DENN;
import pt.ist.ave.jzx.Instructions.ld.LD_EA;
import pt.ist.ave.jzx.Instructions.ld.LD_HA;
import pt.ist.ave.jzx.Instructions.ld.LD_HLMNN;
import pt.ist.ave.jzx.Instructions.ld.LD_HLNN;
import pt.ist.ave.jzx.Instructions.ld.LD_HN;
import pt.ist.ave.jzx.Instructions.ld.LD_LA;
import pt.ist.ave.jzx.Instructions.ld.LD_LB;
import pt.ist.ave.jzx.Instructions.ld.LD_LC;
import pt.ist.ave.jzx.Instructions.ld.LD_LN;
import pt.ist.ave.jzx.Instructions.ld.LD_MBCA;
import pt.ist.ave.jzx.Instructions.ld.LD_MDEA;
import pt.ist.ave.jzx.Instructions.ld.LD_MHLA;
import pt.ist.ave.jzx.Instructions.ld.LD_MHLD;
import pt.ist.ave.jzx.Instructions.ld.LD_MHLE;
import pt.ist.ave.jzx.Instructions.ld.LD_MNNA;
import pt.ist.ave.jzx.Instructions.ld.LD_MNNHL;
import pt.ist.ave.jzx.Instructions.ld.LD_SPHL;

public class InstructionFactory {

	public static Instruction getInstruction(short opCode) {
		switch(opCode) {
		case 0x76:
			return new HALT(opCode);
		case 0x20:
			return new JRNZ_D(opCode);
		case 0x0b:
			return new DEC_BC(opCode);
		case 0x78:
			return new LD_AB(opCode);
		case 0xb1:
			return new OR_C(opCode);
		case 0xd9:
			return new EXX(opCode);
		case 0xc1:
			return new POP_BC(opCode);
		case 0x12:
			return new LD_MDEA(opCode);
		case 0xd5:
			return new PUSH_DE(opCode);
		case 0xc5:
			return new PUSH_BC(opCode);
		case 0xd1:
			return new POP_DE(opCode);
		case 0x08:
			return new EX_AFAF(opCode);
		case 0xf1:
			return new POP_AF(opCode);
		case 0xf5:
			return new PUSH_AF(opCode);
		case 0x7e:
			return new LD_AMHL(opCode);
		case 0xe1:
			return new POP_HL(opCode);
		case 0x10:
			return new DJNZ_D(opCode);
		case 0x24:
			return new INC_H(opCode);
		case 0x3a:
			return new LD_AMNN(opCode);
		case 0xa7:
			return new AND_A(opCode);
		case 0x3d:
			return new DEC_A(opCode);
		case 0x28:
			return new JRZ_D(opCode);
		case 0xcd:
			return new CALL_NN(opCode);
		case 0xc8:
			return new RETZ(opCode);
		case 0x23:
			return new INC_HL(opCode);
		case 0xb0:
			return new OR_B(opCode);
		case 0xa1:
			return new AND_C(opCode);
		case 0x13:
			return new INC_DE(opCode);
		case 0xfe:
			return new CP_N(opCode);
		case 0x21:
			return new LD_HLNN(opCode);
		case 0x7a:
			return new LD_AD(opCode);
		case 0xc2:
			return new JPNZ_NN(opCode);
		case 0xe6:
			return new AND_N(opCode);
		case 0x38:
			return new JRC_D(opCode);
		case 0x1b:
			return new DEC_DE(opCode);
		case 0x2b:
			return new DEC_HL(opCode);
		case 0x14:
			return new INC_D(opCode);
		case 0x4a:
			return new LD_CD(opCode);
		case 0xc3:
			return new JP_NN(opCode);
		case 0x2c:
			return new INC_L(opCode);
		case 0x68:
			return new LD_LB(opCode);
		case 0xb6:
			return new OR_MHL(opCode);
		case 0xa6:
			return new AND_MHL(opCode);
		case 0x69:
			return new LD_LC(opCode);
		case 0xa5:
			return new AND_L(opCode);
		case 0x1c:
			return new INC_E(opCode);
		case 0xb4:
			return new OR_H(opCode);
		case 0x1a:
			return new LD_AMDE(opCode);
		case 0x02:
			return new LD_MBCA(opCode);
		case 0x77:
			return new LD_MHLA(opCode);
		case 0xf9:
			return new LD_SPHL(opCode);
		case 0xe5:
			return new PUSH_HL(opCode);
		case 0x01:
			return new LD_BCNN(opCode);
		case 0x19:
			return new ADD_HLDE(opCode);
		case 0x3e:
			return new LD_AN(opCode);
		case 0x2a:
			return new LD_HLMNN(opCode);
		case 0x11:
			return new LD_DENN(opCode);
		case 0xbb:
			return new CP_E(opCode);
		case 0x32:
			return new LD_MNNA(opCode);
		case 0x22:
			return new LD_MNNHL(opCode);
		case 0xc6:
			return new ADD_AN(opCode);
		case 0x83:
			return new ADD_AE(opCode);
		case 0xc9:
			return new RET(opCode);
		case 0x4f:
			return new LD_CA(opCode);
		case 0x5f:
			return new LD_EA(opCode);
		case 0xb9:
			return new CP_C(opCode);
		case 0x46:
			return new LD_BMHL(opCode);
		case 0x26:
			return new LD_HN(opCode);
		case 0xca:
			return new JPZ_NN(opCode);
		case 0x86:
			return new ADD_AMHL(opCode);
		case 0x73:
			return new LD_MHLE(opCode);
		case 0x72:
			return new LD_MHLD(opCode);
//		case 0x06:
//			return new LD_BN(opCode);
		case 0xc0:
			return new RETNZ(opCode);
		case 0x4e:
			return new LD_CMHL(opCode);
		case 0x67:
			return new LD_HA(opCode);
		case 0x85:
			return new ADD_AL(opCode);
		case 0x2e:
			return new LD_LN(opCode);
//		case 0xf2:
//			return new JPP_NN(opCode);
//		case 0x09:
//			return new ADD_HLBC(opCode);
		case 0xaf:
			return new XOR_A(opCode);
		case 0x3c:
			return new INC_A(opCode);
		case 0x7c:
			return new LD_AH(opCode);
		case 0x90:
			return new SUB_B(opCode);
		case 0x91:
			return new SUB_C(opCode);
		case 0x25:
			return new DEC_H(opCode);
		case 0x95:
			return new SUB_L(opCode);
		case 0xd6:
			return new SUB_N(opCode);
		case 0x7d:
			return new LD_AL(opCode);
		case 0xbd:
			return new CP_L(opCode);
		case 0x47:
			return new LD_BA(opCode);
		case 0x81:
			return new ADD_AC(opCode);
		case 0x29:
			return new ADD_HLHL(opCode);
		case 0xb7:
			return new OR_A(opCode);
		case 0x18:
			return new JR_D(opCode);
		case 0x6f:
			return new LD_LA(opCode);
		case 0x0f:
			return new RRCA(opCode);
		case 0x30:
			return new JRNC_D(opCode);
		case 0x1d:
			return new DEC_E(opCode);
		case 0x87:
			return new ADD_AA(opCode);
		case 0x2d:
			return new DEC_L(opCode);
		case 0x0d:
			return new DEC_C(opCode);
		case 0xdb:
			return new IN_AN(opCode);
		case 0x15:
			return new DEC_D(opCode);
		case 0x79:
			return new LD_AC(opCode);			
		}
		return null;
	}
}
