package pt.ist.ave.jzx.Instructions;

public class DEC_E extends Instruction {

	public DEC_E(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
//		m_e8 = dec8(m_e8);
		_cpu.setM_e8(_cpu.dec8(_cpu.getM_a8()));
		
	}

	@Override
	public int incTstates() {
//		m_tstates += 4;
		return 4;
	}

}
