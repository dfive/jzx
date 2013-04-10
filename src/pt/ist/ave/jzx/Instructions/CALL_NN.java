package pt.ist.ave.jzx.Instructions;

public class CALL_NN extends Instruction {

	public CALL_NN(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
//		m_tstates += 17;
//		push(incinc16(m_pc16));
//		m_pc16 = m_memory.read16(m_pc16);
		_cpu.push(_cpu.incinc16(_cpu.getM_pc16()));
		_cpu.setM_pc16(_cpu.getM_memory().read16(_cpu.getM_pc16()));
	}

	@Override
	public int incTstates() {
		return 17;
	}

}
