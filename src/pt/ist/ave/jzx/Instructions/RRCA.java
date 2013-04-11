package pt.ist.ave.jzx.Instructions;

import pt.ist.ave.jzx.Z80;

public class RRCA extends Instruction {

	public RRCA(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		//m_carryF = ((m_a8 & 0x01) != 0);
		_cpu.setM_carryF( (_cpu.getM_a8() & 0x01) !=0 );
		//m_a8 = (m_a8 >> 1) | ((m_carryF ? 1 : 0) << 7);
		_cpu.setM_a8( (_cpu.getM_a8()>>1) | ((_cpu.getM_carryF() ? 1 : 0)) << 7 );
		//m_halfcarryF = false;
		_cpu.setM_halfcarryF(false);
		//m_addsubtractF = false;
		_cpu.setM_addsubtractF(false);
		//m_3F = ((m_a8 & THREE_MASK) != 0);
		_cpu.setM_3F( (_cpu.getM_a8() & Z80.THREE_MASK) != 0 ); 
		//m_5F = ((m_a8 & FIVE_MASK) != 0);
		_cpu.setM_5F((_cpu.getM_a8() & Z80.FIVE_MASK) != 0);
		
		
	}

	@Override
	public int incTstates() {
//		m_tstates += 4;
		return 4;
	}

}
