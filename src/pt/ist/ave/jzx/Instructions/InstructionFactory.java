package pt.ist.ave.jzx.Instructions;

import pt.ist.ave.jzx.Instructions.ld.LD_AB;
import pt.ist.ave.jzx.Instructions.ld.LD_AC;
import pt.ist.ave.jzx.Instructions.ld.LD_AD;
import pt.ist.ave.jzx.Instructions.ld.LD_AH;
import pt.ist.ave.jzx.Instructions.ld.LD_AL;
import pt.ist.ave.jzx.Instructions.ld.LD_AMBC;
import pt.ist.ave.jzx.Instructions.ld.LD_AMDE;
import pt.ist.ave.jzx.Instructions.ld.LD_AMHL;
import pt.ist.ave.jzx.Instructions.ld.LD_AMNN;
import pt.ist.ave.jzx.Instructions.ld.LD_AN;
import pt.ist.ave.jzx.Instructions.ld.LD_BA;
import pt.ist.ave.jzx.Instructions.ld.LD_BB;
import pt.ist.ave.jzx.Instructions.ld.LD_BC;
import pt.ist.ave.jzx.Instructions.ld.LD_BCNN;
import pt.ist.ave.jzx.Instructions.ld.LD_BD;
import pt.ist.ave.jzx.Instructions.ld.LD_BE;
import pt.ist.ave.jzx.Instructions.ld.LD_BH;
import pt.ist.ave.jzx.Instructions.ld.LD_BL;
import pt.ist.ave.jzx.Instructions.ld.LD_BMHL;
import pt.ist.ave.jzx.Instructions.ld.LD_BN;
import pt.ist.ave.jzx.Instructions.ld.LD_CA;
import pt.ist.ave.jzx.Instructions.ld.LD_CB;
import pt.ist.ave.jzx.Instructions.ld.LD_CC;
import pt.ist.ave.jzx.Instructions.ld.LD_CD;
import pt.ist.ave.jzx.Instructions.ld.LD_CE;
import pt.ist.ave.jzx.Instructions.ld.LD_CH;
import pt.ist.ave.jzx.Instructions.ld.LD_CL;
import pt.ist.ave.jzx.Instructions.ld.LD_CMHL;
import pt.ist.ave.jzx.Instructions.ld.LD_DENN;
import pt.ist.ave.jzx.Instructions.ld.LD_DN;
import pt.ist.ave.jzx.Instructions.ld.LD_EA;
import pt.ist.ave.jzx.Instructions.ld.LD_EN;
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
import pt.ist.ave.jzx.Instructions.ld.LD_MHLN;
import pt.ist.ave.jzx.Instructions.ld.LD_MNNA;
import pt.ist.ave.jzx.Instructions.ld.LD_MNNHL;
import pt.ist.ave.jzx.Instructions.ld.LD_SPHL;
import pt.ist.ave.jzx.Instructions.ld.LD_SPNN;

public class InstructionFactory {

	public static Instruction getInstruction(short opCode) {
		switch(opCode) {
		case 0x01:
			return new LD_BCNN(opCode);
		case 0x02:
			return new LD_MBCA(opCode);
		case 0x03:
			return new INC_BC(opCode);
		case 0x04:
			return new INC_B(opCode);
		case 0x05:
			return new DEC_B(opCode);
		case 0x06:
			return new LD_BN(opCode);
		case 0x07:
			return new RLCA(opCode);
		case 0x08:
			return new EX_AFAF(opCode);
		case 0x09:
			return new ADD_HLBC(opCode);
		case 0x0a:
			return new LD_AMBC(opCode);
		case 0x0b:
			return new DEC_BC(opCode);
		case 0x0c:
			return new INC_C(opCode);
		case 0x0d:
			return new DEC_C(opCode);
		case 0x0e:
			return new LD_CN(opCode);
		case 0x0f:
			return new RRCA(opCode);
		case 0x10:
			return new DJNZ_D(opCode);
		case 0x11:
			return new LD_DENN(opCode);
		case 0x12:
			return new LD_MDEA(opCode);
		case 0x13:
			return new INC_DE(opCode);
		case 0x14:
			return new INC_D(opCode);
		case 0x15:
			return new DEC_D(opCode);
		case 0x16:
			return new LD_DN(opCode);
		case 0x17:
			return new RLA(opCode);
		case 0x18:
			return new JR_D(opCode);
		case 0x19:
			return new ADD_HLDE(opCode);
		case 0x1a:
			return new LD_AMDE(opCode);
		case 0x1b:
			return new DEC_DE(opCode);
		case 0x1c:
			return new INC_E(opCode);
		case 0x1d:
			return new DEC_E(opCode);
		case 0x1e:
			return new LD_EN(opCode);
		case 0x1f:
			return new RRA(opCode);
		case 0x20:
			return new JRNZ_D(opCode);
		case 0x21:
			return new LD_HLNN(opCode);
		case 0x22:
			return new LD_MNNHL(opCode);
		case 0x23:
			return new INC_HL(opCode);
		case 0x24:
			return new INC_H(opCode);
		case 0x25:
			return new DEC_H(opCode);
		case 0x26:
			return new LD_HN(opCode);
		case 0x27:
			return new DAA(opCode);
		case 0x28:
			return new JRZ_D(opCode);
		case 0x29:
			return new ADD_HLHL(opCode);
		case 0x2a:
			return new LD_HLMNN(opCode);
		case 0x2b:
			return new DEC_HL(opCode);
		case 0x2c:
			return new INC_L(opCode);
		case 0x2d:
			return new DEC_L(opCode);
		case 0x2e:
			return new LD_LN(opCode);
		case 0x2f:
			return new CPL(opCode);
		case 0x30:
			return new JRNC_D(opCode);
		case 0x31:
			return new LD_SPNN(opCode);
		case 0x32:
			return new LD_MNNA(opCode);
		case 0x33:
			return new INC_SP(opCode);
		case 0x34:
			return new INC_MHL(opCode);
		case 0x35:
			return new DEC_MHL(opCode);
		case 0x36:
			return new LD_MHLN(opCode);
		case 0x37:
			return new SCF(opCode);
		case 0x38:
			return new JRC_D(opCode);
		case 0x39:
			return new ADD_HLSP(opCode);
		case 0x3a:
			return new LD_AMNN(opCode);
		case 0x3b:
			return new DEC_SP(opCode);
		case 0x3c:
			return new INC_A(opCode);
		case 0x3d:
			return new DEC_A(opCode);
		case 0x3e:
			return new LD_AN(opCode);
		case 0x3f:
			return new CCF(opCode);
		case 0x40:
			return new LD_BB(opCode);
		case 0x41:
			return new LD_BC(opCode);
		case 0x42:
			return new LD_BD(opCode);
		case 0x43:
			return new LD_BE(opCode);
		case 0x44:
			return new LD_BH(opCode);
		case 0x45:
			return new LD_BL(opCode);
		case 0x46:
			return new LD_BMHL(opCode);
		case 0x47:
			return new LD_BA(opCode);
		case 0x48:
			return new LD_CB(opCode);
		case 0x49:
			return new LD_CC(opCode);
		case 0x4a:
			return new LD_CD(opCode);
		case 0x4b:
			return new LD_CE(opCode);
		case 0x4c:
			return new LD_CH(opCode);
		case 0x4d:
			return new LD_CL(opCode);
		case 0x4e:
			return new LD_CMHL(opCode);
		case 0x4f:
			return new LD_CA(opCode);
			//		case 0x50:
			//		case 0x51:
			//		case 0x52:
			//		case 0x53:
			//		case 0x54:
			//		case 0x55:
			//		case 0x56:
			//		case 0x57:
			//		case 0x58:
			//		case 0x59:
			//		case 0x5a:
			//		case 0x5b:
			//		case 0x5c:
			//		case 0x5d:
			//		case 0x5e:
		case 0x5f:
			return new LD_EA(opCode);
			//		case 0x61:
			//		case 0x62:
			//		case 0x63:
			//		case 0x64:
			//		case 0x65:
			//		case 0x66:
		case 0x67:
			return new LD_HA(opCode);
		case 0x68:
			return new LD_LB(opCode);
		case 0x69:
			return new LD_LC(opCode);
			//		case 0x6a:
			//		case 0x6b:
			//		case 0x6c:
			//		case 0x6d:
			//		case 0x6e:
		case 0x6f:
			return new LD_LA(opCode);
			//		case 0x71:
		case 0x72:
			return new LD_MHLD(opCode);
		case 0x73:
			return new LD_MHLE(opCode);
			//		case 0x74:
			//		case 0x75:
		case 0x76:
			return new HALT(opCode);
		case 0x77:
			return new LD_MHLA(opCode);
		case 0x78:
			return new LD_AB(opCode);
		case 0x79:
			return new LD_AC(opCode);			
		case 0x7a:
			return new LD_AD(opCode);
			//		case 0x7b:
		case 0x7c:
			return new LD_AH(opCode);
		case 0x7d:
			return new LD_AL(opCode);
		case 0x7e:
			return new LD_AMHL(opCode);
			//		case 0x7f:
			//		case 0x80:
		case 0x81:
			return new ADD_AC(opCode);
			//		case 0x82:
		case 0x83:
			return new ADD_AE(opCode);
			//		case 0x84:
		case 0x85:
			return new ADD_AL(opCode);
		case 0x86:
			return new ADD_AMHL(opCode);
		case 0x87:
			return new ADD_AA(opCode);
			//		case 0x88:
			//		case 0x89:
			//		case 0x8a:
			//		case 0x8b:
			//		case 0x8c:
			//		case 0x8d:
			//		case 0x8e:
			//		case 0x8f:
		case 0x90:
			return new SUB_B(opCode);
		case 0x91:
			return new SUB_C(opCode);
			//		case 0x92:
			//		case 0x93:
			//		case 0x94:
		case 0x95:
			return new SUB_L(opCode);
			//		case 0x96:
			//		case 0x97:
			//		case 0x98:
			//		case 0x99:
			//		case 0x9a:
			//		case 0x9b:
			//		case 0x9c:
			//		case 0x9d:
			//		case 0x9e:
			//		case 0x9f:
			//		case 0xa0:
		case 0xa1:
			return new AND_C(opCode);
			//		case 0xa2:
			//		case 0xa3:
			//		case 0xa4:
		case 0xa5:
			return new AND_L(opCode);
		case 0xa6:
			return new AND_MHL(opCode);
		case 0xa7:
			return new AND_A(opCode);
			//		case 0xa8:
			//		case 0xa9:
			//		case 0xaa:
			//		case 0xab:
			//		case 0xac:
			//		case 0xad:
			//		case 0xae:
		case 0xaf:
			return new XOR_A(opCode);
		case 0xb0:
			return new OR_B(opCode);
		case 0xb1:
			return new OR_C(opCode);
			//		case 0xb2:
			//		case 0xb3:
		case 0xb4:
			return new OR_H(opCode);
			//		case 0xb5:
		case 0xb6:
			return new OR_MHL(opCode);
		case 0xb7:
			return new OR_A(opCode);
			//		case 0xb8:
		case 0xb9:
			return new CP_C(opCode);
			//		case 0xba:
		case 0xbb:
			return new CP_E(opCode);
			//		case 0xbc:
		case 0xbd:
			return new CP_L(opCode);
			//		case 0xbe:
			//		case 0xbf:
		case 0xc0:
			return new RETNZ(opCode);
		case 0xc1:
			return new POP_BC(opCode);
		case 0xc2:
			return new JPNZ_NN(opCode);
		case 0xc3:
			return new JP_NN(opCode);
			//		case 0xc4:
		case 0xc5:
			return new PUSH_BC(opCode);
		case 0xc6:
			return new ADD_AN(opCode);
			//		case 0xc7:
		case 0xc8:
			return new RETZ(opCode);
		case 0xc9:
			return new RET(opCode);
		case 0xca:
			return new JPZ_NN(opCode);
		case 0xcb:
			return new DECODECB(opCode);

			//		case 0xcc:
		case 0xcd:
			return new CALL_NN(opCode);
			//		case 0xce:
			//		case 0xcf:
			//		case 0xd0:
		case 0xd1:
			return new POP_DE(opCode);
			//		case 0xd2:
			//		case 0xd3:
			//		case 0xd4:
		case 0xd5:
			return new PUSH_DE(opCode);
		case 0xd6:
			return new SUB_N(opCode);
			//		case 0xd7:
			//		case 0xd8:
		case 0xd9:
			return new EXX(opCode);
			//		case 0xda:
		case 0xdb:
			return new IN_AN(opCode);
			//		case 0xdc:
			//		case 0xdd:
			//		case 0xde:
			//		case 0xdf:
			//		case 0xe0:
		case 0xe1:
			return new POP_HL(opCode);
			//		case 0xe2:
			//		case 0xe3:
			//		case 0xe4:
		case 0xe5:
			return new PUSH_HL(opCode);
		case 0xe6:
			return new AND_N(opCode);
			//		case 0xe7:
			//		case 0xe8:
			//		case 0xe9:
			//		case 0xea:
			//		case 0xeb:
			//		case 0xec:
			//		case 0xed:
			//		case 0xee:
			//		case 0xef:
			//		case 0xf0:
		case 0xf1:
			return new POP_AF(opCode);
		case 0xf2:
			return new JPP_NN(opCode);
			//		case 0xf3:
			//		case 0xf4:
		case 0xf5:
			return new PUSH_AF(opCode);
			//		case 0xf6:
			//		case 0xf7:
			//		case 0xf8:
		case 0xf9:
			return new LD_SPHL(opCode);
			//		case 0xfa:
			//		case 0xfb:
			//		case 0xfc:
			//		case 0xfd:
		case 0xfe:
			return new CP_N(opCode);
		case 0xff:
		}
		return null;
	}
}
