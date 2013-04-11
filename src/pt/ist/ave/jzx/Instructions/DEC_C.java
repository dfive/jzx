package pt.ist.ave.jzx.Instructions;

public class DEC_C extends Instruction {

	public DEC_C(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
//		m_c8 = dec8(m_c8);
		_cpu.setM_c8(_cpu.dec8(_cpu.getM_a8()));
		
	}

	@Override
	public int incTstates() {
//		m_tstates += 4;
		return 4;
	}

}
